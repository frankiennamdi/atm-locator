package com.franklin.sample.bankbase.atm.api;

import com.franklin.sample.bankbase.atm.service.ATMInfo;
import com.franklin.sample.bankbase.atm.service.LocationService;
import com.franklin.sample.bankbase.atm.support.CastSupport;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationListingRouteHandler {
  private final LocationService locationService;

  @Autowired
  public LocationListingRouteHandler(LocationService locationService) {
    this.locationService = locationService;
  }

  @Handler
  public List<ATMInfo> getListOfATM(Exchange exchange) {
    Class<List<String>> clazz = CastSupport.cast(List.class);
    List<String> cities = exchange.getIn().getBody(clazz);
    return locationService.find(cities);
  }
}
