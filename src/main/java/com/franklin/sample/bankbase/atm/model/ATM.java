package com.franklin.sample.bankbase.atm.model;

import javax.persistence.*;

@Entity
@Table(name = "ATM")
public class ATM {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  private String addressStreet;

  private String addressHouseNumber;

  private String addressPostalCode;

  private String addressCity;

  private String addressGeolocationLat;

  private String addressGeolocationLng;

  private int distance;

  private String type;

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getAddressStreet() {
    return addressStreet;
  }

  public void setAddressStreet(final String addressStreet) {
    this.addressStreet = addressStreet;
  }

  public String getAddressHouseNumber() {
    return addressHouseNumber;
  }

  public void setAddressHouseNumber(final String addressHouseNumber) {
    this.addressHouseNumber = addressHouseNumber;
  }

  public String getAddressPostalCode() {
    return addressPostalCode;
  }

  public void setAddressPostalCode(final String addressPostalCode) {
    this.addressPostalCode = addressPostalCode;
  }

  public String getAddressCity() {
    return addressCity;
  }

  public void setAddressCity(final String addressCity) {
    this.addressCity = addressCity;
  }

  public String getAddressGeolocationLat() {
    return addressGeolocationLat;
  }

  public void setAddressGeolocationLat(final String addressGeolocationLat) {
    this.addressGeolocationLat = addressGeolocationLat;
  }

  public String getAddressGeolocationLng() {
    return addressGeolocationLng;
  }

  public void setAddressGeolocationLng(final String addressGeolocationLng) {
    this.addressGeolocationLng = addressGeolocationLng;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(final int distance) {
    this.distance = distance;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }
}
