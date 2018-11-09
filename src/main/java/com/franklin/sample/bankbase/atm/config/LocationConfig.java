package com.franklin.sample.bankbase.atm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "atm-locator")
@Configuration
public class LocationConfig
{

  private String apiAddress;

  public String getApiAddress() {
    return apiAddress;
  }

  public void setApiAddress(final String apiAddress) {
    this.apiAddress = apiAddress;
  }
}
