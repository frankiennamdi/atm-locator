package com.franklin.sample.bankbase.atm.api;

import com.franklin.sample.bankbase.atm.Application;
import com.franklin.sample.bankbase.atm.repository.LocationRepository;
import com.franklin.sample.bankbase.atm.service.ATMInfo;
import com.franklin.sample.bankbase.atm.support.RouteDispatcher;
import com.google.common.collect.ImmutableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8888)
@TestPropertySource(properties = {"atm-locator.apiAddress=http://localhost:8888/api/locator/atms/"})
public class LocationRouteTest {

  @Autowired
  private RouteDispatcher routeDispatcher;

  @Test
  @WithMockUser(username = "api", roles = {"API"})
  public void testCanCreateLocation() throws Exception {
    stubFor(get(urlEqualTo("/api/locator/atms/"))
            .willReturn(aResponse().withHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .withBodyFile("service/Schiphol_ATM_Locations.txt")));
    ATMInfo atmInfo = new ATMInfo("Aankomstpassage", "1", "1118 AX",
            "TestCity", "52.307138", "4.760019", 0, "ING");
    List<ATMInfo> atmInfoList = routeDispatcher
            .exchangeForList(LocationRoute.ATM_LOCATION_CREATION_ENDPOINT, ImmutableList.of(atmInfo));
    assertThat(atmInfoList.size(), is(1));
    assertThat(atmInfoList, everyItem(isIn(ImmutableList.of(atmInfo))));
  }
}
