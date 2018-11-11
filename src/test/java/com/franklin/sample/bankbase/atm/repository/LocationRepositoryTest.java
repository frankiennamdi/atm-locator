package com.franklin.sample.bankbase.atm.repository;

import com.franklin.sample.bankbase.atm.Application;
import com.franklin.sample.bankbase.atm.model.ATM;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@Transactional
public class LocationRepositoryTest {

  @Autowired
  private LocationRepository locationRepository;

  @Test
  public void testCanCreateAndFindAtm() {
    ATM atm = new ATM();
    atm.setAddressStreet("Aankomstpassage");
    atm.setAddressHouseNumber("1");
    atm.setAddressPostalCode("1118 AX");
    atm.setAddressCity("MyCity");
    atm.setAddressGeolocationLat("52.307138");
    atm.setAddressGeolocationLng("4.760019");
    atm.setDistance(0);
    atm.setType("ING");

    List<ATM> items = locationRepository.findByAddressCityIn(ImmutableList.of("Schiphol"));

    assertThat(items, everyItem(isIn(ImmutableList.of(atm))));
  }
}
