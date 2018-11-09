package com.franklin.sample.bankbase.atm.controller;


import com.franklin.sample.bankbase.atm.api.LocationRoute;
import com.franklin.sample.bankbase.atm.service.ATMInfo;
import com.franklin.sample.bankbase.atm.support.RouteDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller that returns a {@link ModelAndView} on success
 */
@RestController
@RequestMapping("/cities")
public class LocationController {

  private final RouteDispatcher routeDispatcher;

  @Autowired
  public LocationController(RouteDispatcher routeDispatcher) {
    this.routeDispatcher = routeDispatcher;
  }

  @GetMapping("/{city}")
  public ModelAndView listAtmInCity(@PathVariable String city) throws Exception {
    List<ATMInfo> atmList = routeDispatcher.exchangeForList(LocationRoute.ATM_LOCATION_LISTING_ENDPOINT, city);
    Map<String, Object> params = new HashMap<>();
    params.put("atmList", atmList);
    return new ModelAndView("locations", params);
  }
}
