package com.franklin.sample.bankbase.atm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.franklin.sample.bankbase.atm.config.LocationConfig;
import com.franklin.sample.bankbase.atm.model.ATM;
import com.franklin.sample.bankbase.atm.support.AtmDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExternalATMLocationService
{

  private final ObjectMapper mapper;

  private final RestTemplate restTemplate;

  private final String atmServiceUrl;

  @Autowired
  public ExternalATMLocationService(LocationConfig locationConfig) {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(ATMInfo.class, new AtmDeserializer());
    mapper.registerModule(module);
    restTemplate = new RestTemplate();
    this.atmServiceUrl = locationConfig.getApiAddress();
  }

  public List<ATMInfo> findAtmByCity(List<String> cities) {
    ResponseEntity<String> rateResponse = restTemplate.exchange(atmServiceUrl, HttpMethod.GET, null,
        String.class);
    String json = rateResponse.getBody();
    try {
      Set<String> distinctCities = cities.stream().distinct().collect(Collectors.toSet());
      String cleaned = StringUtils.substringAfter(json, "\n");
      List<ATMInfo> atmInfoList = mapper.readValue(cleaned, new TypeReference<List<ATMInfo>>() { });
      return atmInfoList.stream().filter(atm -> distinctCities.contains(atm.getAddressCity())).collect(Collectors.toList());
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
