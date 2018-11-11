package com.franklin.sample.bankbase.atm.controller;

import com.franklin.sample.bankbase.atm.Application;
import com.franklin.sample.bankbase.atm.repository.LocationRepository;
import com.franklin.sample.bankbase.atm.service.ATMInfo;
import com.franklin.sample.bankbase.atm.support.CastSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8888)
@TestPropertySource(properties = {"atm-locator.apiAddress=http://localhost:8888/api/locator/atms/"})
@Transactional
public class LocationControllerTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();
    stubFor(get(urlEqualTo("/api/locator/atms/"))
            .willReturn(aResponse().withHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .withBodyFile("service/Schiphol_ATM_Locations.txt")));
  }

  @Test
  @WithMockUser(username = "user")
  public void testListAtmInCity_withUnauthorizedUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/cities/london"))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(username = "api", roles = {"API"})
  public void testListAtmInCity_withAuthorizedUser() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cities/Schiphol"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    assertThat(mvcResult.getModelAndView().getViewName(), notNullValue());
    assertThat(mvcResult.getModelAndView().getViewName(), is("locations"));
    assertThat(mvcResult.getModelAndView().getModelMap().get("atmList"), notNullValue());
    List<ATMInfo> atmList = CastSupport.cast(mvcResult.getModelAndView().getModelMap().get("atmList"));
    assertThat(atmList.stream().map(ATMInfo::getAddressCity).collect(Collectors.toSet()).size(), is(1));
  }
}
