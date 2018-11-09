package com.franklin.sample.bankbase.atm.repository;

import java.util.List;

import com.franklin.sample.bankbase.atm.model.ATM;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository
        extends CrudRepository<ATM, Integer> {

  List<ATM> findByAddressCityIn(List<String> cities);

  @Query(value = "SELECT id from ATM a " +
          " where a.addressStreet = :#{#atm.addressStreet} and " +
          " lower(a.addressHouseNumber) = lower(:#{#atm.addressHouseNumber}) and " +
          " lower(a.addressPostalCode) = lower(:#{#atm.addressPostalCode}) and " +
          " lower(a.addressCity) = lower(:#{#atm.addressCity}) and " +
          " a.addressGeolocationLat = :#{#atm.addressGeolocationLat} and " +
          " a.addressGeolocationLng = :#{#atm.addressGeolocationLng} and " +
          " a.distance = :#{#atm.distance} and " +
          " lower(a.type) = lower(:#{#atm.type}) ")
  List<Integer> findByUniqueKey(@Param("atm") ATM atm);
}
