package com.franklin.sample.bankbase.atm.service;

import com.franklin.sample.bankbase.atm.model.ATM;
import com.franklin.sample.bankbase.atm.repository.LocationRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RolesAllowed("API")
public class LocationService {

  private final LocationRepository locationRepository;

  private final ExternalATMLocationService externalATMLocationService;

  @Autowired
  public LocationService(LocationRepository locationRepository, ExternalATMLocationService externalATMLocationService) {
    this.locationRepository = locationRepository;
    this.externalATMLocationService = externalATMLocationService;
  }

  public List<ATMInfo> save(List<ATMInfo> atmList) {
    Iterable<ATM> atmIterable = ImmutableList.copyOf(atmList)
            .stream()
            .distinct()
            .map(this::toATM)
            .collect(Collectors.toList());

    for (ATM atm : atmIterable) {
      List<Integer> ids = locationRepository.findByUniqueKey(atm);
      if (ids.size() > 0) {
        atm.setId(ids.get(0));
      }
    }
    return Lists.newArrayList(locationRepository.saveAll(atmIterable))
            .stream()
            .map(this::toATMInfo)
            .collect(Collectors.toList());
  }

  public List<ATMInfo> find(List<String> cities) {
    List<ATM> locallySavedATMInfo = locationRepository.findByAddressCityIn(cities);
    List<ATMInfo> atmInfoList = locallySavedATMInfo.stream().map(this::toATMInfo).collect(Collectors.toList());
    atmInfoList.addAll(externalATMLocationService.findAtmByCity(cities));
    return atmInfoList.stream().distinct().collect(Collectors.toList());
  }

  private ATM toATM(ATMInfo atmInfo) {
    ATM atm = new ATM();
    atm.setAddressStreet(atmInfo.getAddressStreet());
    atm.setAddressHouseNumber(atmInfo.getAddressHouseNumber());
    atm.setAddressPostalCode(atmInfo.getAddressPostalCode());
    atm.setAddressCity(atmInfo.getAddressCity());
    atm.setAddressGeolocationLat(atmInfo.getAddressGeolocationLat());
    atm.setAddressGeolocationLng(atmInfo.getAddressGeolocationLng());
    atm.setDistance(atmInfo.getDistance());
    atm.setType(atmInfo.getType());
    return atm;
  }

  private ATMInfo toATMInfo(ATM atm) {
    return new ATMInfo(
            atm.getAddressStreet(),
            atm.getAddressHouseNumber(),
            atm.getAddressPostalCode(),
            atm.getAddressCity(),
            atm.getAddressGeolocationLat(),
            atm.getAddressGeolocationLng(),
            atm.getDistance(),
            atm.getType());
  }
}
