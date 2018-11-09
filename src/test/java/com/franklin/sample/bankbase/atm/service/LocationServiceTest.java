package com.franklin.sample.bankbase.atm.service;

import com.franklin.sample.bankbase.atm.Application;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8888)
@TestPropertySource(properties = {"atm-locator.apiAddress=http://localhost:8888/api/locator/atms/"})
@Transactional
public class LocationServiceTest {

  @Autowired
  private LocationService locationService;

  @Test
  @WithMockUser(username = "api", roles = {"API"})
  public void testCanCreateUpdateAndFindAtm_andLocalDuplicatesAreNotReturned() {
    stubFor(get(urlEqualTo("/api/locator/atms/"))
            .willReturn(aResponse().withHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .withBodyFile("service/Schiphol_ATM_Locations.txt")));
    ATMInfo localDuplicateATMInfo = new ATMInfo("Aankomstpassage", "1", "1118 AX",
            "Schiphol", "52.307138", "4.760019", 0, "ING");

    List<ATMInfo> locallySavedATMList = locationService.save(ImmutableList.of(localDuplicateATMInfo));
    assertThat(locallySavedATMList.size(), is(1));
    List<ATMInfo> currentATMList = locationService.find(ImmutableList.of("Schiphol"));
    assertThat(currentATMList.size(), is(2));
  }

  @Test
  @WithMockUser(username = "api", roles = {"API"})
  public void testCanCreateUpdateAndFindAtm_andLocalDuplicatesWithLetterCaseDifferenceAreNotReturned() {
    stubFor(get(urlEqualTo("/api/locator/atms/"))
            .willReturn(aResponse().withHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .withBodyFile("service/Schiphol_ATM_Locations.txt")));

    ATMInfo localDuplicateATMInfo = new ATMInfo("Aankomstpassage", "1", "1118 AX",
            "schiphol", "52.307138", "4.760019", 0, "ING");

    List<ATMInfo> locallySavedATMList = locationService.save(ImmutableList.of(localDuplicateATMInfo));
    assertThat(locallySavedATMList.size(), is(1));
    List<ATMInfo> currentATMList = locationService.find(ImmutableList.of("Schiphol"));
    assertThat(currentATMList.size(), is(2));
  }
}
