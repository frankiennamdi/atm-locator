package com.franklin.sample.bankbase.atm.api;

import com.franklin.sample.bankbase.atm.service.ATMInfo;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class LocationRoute
    extends RouteBuilder
{

  public static final String ATM_LOCATION_LISTING_ENDPOINT = "direct:atm-location-listing";

  private static final String ATM_LOCATION_LISTING_ROUTE_ID = "AtmLocationListingRoute";

  public static final String ATM_LOCATION_CREATION_ENDPOINT = "direct:atm-location-creation";

  private static final String ATM_LOCATION_CREATION_ROUTE_ID = "AtmLocationCreationRoute";

  @Override
  @SuppressWarnings("deprecation")
  public void configure() {

    restConfiguration()
        .contextPath("/api")
        .apiContextPath("/api-doc")
        .apiProperty("api.title", "ATM LOCATION API")
        .apiProperty("api.version", "1.0")
        .apiProperty("cors", "true")
        .apiContextRouteId("doc-api")
        .bindingMode(RestBindingMode.json);

    rest("/cities").description("atm services")
        .get("/{city}")
        .description("atm locations listing service")
        .route()
        .routeId("location_listing_api")
        .log("listing atm locations")
        .process((exchange) -> exchange.getIn().setBody(exchange.getIn().getHeader("city")))
        .to(ATM_LOCATION_LISTING_ENDPOINT)
        .endRest()

        .post("/")
        .typeList(ATMInfo.class)
        .description("atm locations creation service")
        .route()
        .routeId("location_creation_api")
        .log("creating atm listings")
        .to(ATM_LOCATION_CREATION_ENDPOINT)
        .endRest();

    from(ATM_LOCATION_CREATION_ENDPOINT)
        .id(ATM_LOCATION_CREATION_ROUTE_ID)
        .log("creating locations")
        .bean(LocationCreationRouteHandler.class)
        .to(ATM_LOCATION_LISTING_ENDPOINT);

    from(ATM_LOCATION_LISTING_ENDPOINT)
        .id(ATM_LOCATION_LISTING_ROUTE_ID)
        .log("listing locations")
        .bean(LocationListingRouteHandler.class)
        .process((exchange) -> exchange.getOut().setBody(exchange.getIn().getBody()));
  }
}