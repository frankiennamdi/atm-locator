package com.franklin.sample.bankbase.atm.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklin.sample.bankbase.atm.model.ATM;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

public class ATMInfo {

  private String addressStreet;

  private String addressHouseNumber;

  private String addressPostalCode;

  private String addressCity;

  private String addressGeolocationLat;

  private String addressGeolocationLng;

  private int distance;

  private String type;

  @JsonCreator
  public ATMInfo(@JsonProperty("addressStreet") String addressStreet,
                 @JsonProperty("addressHouseNumber") String addressHouseNumber,
                 @JsonProperty("addressPostalCode") String addressPostalCode,
                 @JsonProperty("addressCity") String addressCity,
                 @JsonProperty("addressGeolocationLat") String addressGeolocationLat,
                 @JsonProperty("addressGeolocationLng") String addressGeolocationLng,
                 @JsonProperty("distance") int distance,
                 @JsonProperty("type") String type) {

    Objects.requireNonNull(addressStreet);
    Objects.requireNonNull(addressHouseNumber);
    Objects.requireNonNull(addressPostalCode);
    Objects.requireNonNull(addressCity);
    Objects.requireNonNull(addressGeolocationLat);
    Objects.requireNonNull(addressGeolocationLng);
    Objects.requireNonNull(type);

    this.addressStreet = addressStreet;
    this.addressHouseNumber = addressHouseNumber;
    this.addressPostalCode = addressPostalCode;
    this.addressCity = addressCity;
    this.addressGeolocationLat = addressGeolocationLat;
    this.addressGeolocationLng = addressGeolocationLng;
    this.distance = distance;
    this.type = type;
  }

  @JsonCreator
  public static ATMInfo create(String jsonString) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(jsonString, ATMInfo.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("Bad json string", e);
    }
  }

  public String getAddressStreet() {
    return addressStreet;
  }

  public String getAddressHouseNumber() {
    return addressHouseNumber;
  }

  public String getAddressPostalCode() {
    return addressPostalCode;
  }

  public String getAddressCity() {
    return addressCity;
  }

  public String getAddressGeolocationLat() {
    return addressGeolocationLat;
  }

  public String getAddressGeolocationLng() {
    return addressGeolocationLng;
  }

  public int getDistance() {
    return distance;
  }

  public String getType() {
    return type;
  }


  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (getClass() != o.getClass()) {
      return false;
    }
    ATMInfo atm = (ATMInfo) o;
    return addressStreet.equalsIgnoreCase(atm.getAddressStreet())
            && addressHouseNumber.equalsIgnoreCase(atm.getAddressHouseNumber())
            && addressPostalCode.equalsIgnoreCase(atm.getAddressPostalCode())
            && addressCity.equalsIgnoreCase(atm.getAddressCity())
            && addressGeolocationLat.equalsIgnoreCase(atm.getAddressGeolocationLat())
            && addressGeolocationLng.equalsIgnoreCase(atm.getAddressGeolocationLng())
            && distance == atm.getDistance()
            && type.equalsIgnoreCase(atm.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressStreet, addressHouseNumber, addressPostalCode, addressCity, addressGeolocationLat,
            addressGeolocationLng,
            distance, type);
  }
}
