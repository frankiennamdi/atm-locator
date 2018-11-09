package com.franklin.sample.bankbase.atm.api;

import com.franklin.sample.bankbase.atm.Application;
import com.franklin.sample.bankbase.atm.model.ATM;
import com.franklin.sample.bankbase.atm.repository.LocationRepository;
import com.franklin.sample.bankbase.atm.service.ATMInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 7777)
@TestPropertySource(properties = {"atm-locator.apiAddress=http://localhost:7777/api/locator/atms/"})
@Transactional
public class LocatorApiTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Before
  public void setUp() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(ImmutableList.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
    restTemplate.getRestTemplate().setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
    restTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("api", "api"));
    stubFor(get(urlEqualTo("/api/locator/atms/"))
            .willReturn(aResponse().withHeader(HttpHeaders.ACCEPT, javax.ws.rs.core.MediaType.APPLICATION_JSON)
                    .withBodyFile("service/Schiphol_ATM_Locations.txt")));
  }

  @Test
  public void testListAtmInCity_withAuthorizedUser() throws Exception {
    ResponseEntity<List<ATM>> rateResponse = restTemplate.exchange("/api/cities/Schiphol",
            HttpMethod.GET, null,
            new ParameterizedTypeReference<List<ATM>>() {
            });
    List<ATM> atmList = rateResponse.getBody();
    Set<String> cities = atmList.stream().map(ATM::getAddressCity).collect(Collectors.toSet());
    assertThat(cities.size(), is(1));
    assertThat(Lists.newArrayList(cities).get(0), is("Schiphol"));
  }

  @Test
  public void testCreateAndListAtmInCity_withAuthorizedUser_andWithDuplicate() throws Exception {
    ATMInfo atmInfo = new ATMInfo("Aankomstpassage", "1", "1118 AX",
            "MyCity", "52.307138", "4.760019", 0, "ING");
    HttpEntity<List<ATMInfo>> request = new HttpEntity<>(ImmutableList.of(atmInfo, atmInfo));
    ResponseEntity<List<ATMInfo>> rateResponse = restTemplate.exchange("/api/cities",
            HttpMethod.POST, request, new ParameterizedTypeReference<List<ATMInfo>>() {
            });
    List<ATMInfo> atmList = rateResponse.getBody();
    assertThat(atmList, everyItem(isIn(ImmutableList.of(atmInfo, atmInfo).stream().distinct().collect(Collectors.toList()))));
  }

}
