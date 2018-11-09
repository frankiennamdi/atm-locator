package com.franklin.sample.bankbase.atm.api;

import java.util.List;
import java.util.stream.Collectors;

import com.franklin.sample.bankbase.atm.service.ATMInfo;
import com.franklin.sample.bankbase.atm.service.LocationService;
import com.franklin.sample.bankbase.atm.support.CastSupport;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationCreationRouteHandler {
  private final LocationService locationService;

  @Autowired
  public LocationCreationRouteHandler(LocationService locationService) {
    this.locationService = locationService;
  }

  @Handler
  public void saveAtm(Exchange exchange) {
    Class<List<ATMInfo>> clazz = CastSupport.cast(List.class);
    List<ATMInfo> saveAtmList = locationService.save(exchange.getIn().getBody(clazz));
    List<String> saveAtmCities = saveAtmList.stream().map(ATMInfo::getAddressCity).collect(Collectors.toList());
    exchange.getIn().setBody(saveAtmCities);
  }
}
