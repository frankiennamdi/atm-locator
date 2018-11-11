package com.franklin.sample.bankbase.atm.service;

import com.franklin.sample.bankbase.atm.config.LocationConfig;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExternalATMLocationServiceIT {
  @Test
  public void testCanFindAtm() {
    LocationConfig locationConfig = new LocationConfig();
    locationConfig.setApiAddress("https://www.ing.nl/api/locator/atms/");

    ExternalATMLocationService externalATMLocationService = new ExternalATMLocationService(locationConfig);
    List<ATMInfo> atmInfoList = externalATMLocationService.findAtmByCity(ImmutableList.of("Schiphol"));
    Set<String> cities = atmInfoList.stream().map(ATMInfo::getAddressCity).collect(Collectors.toSet());

    assertThat(cities.size(), is(1));
  }
}
