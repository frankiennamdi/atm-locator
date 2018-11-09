package com.franklin.sample.bankbase.atm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig
    extends WebSecurityConfigurerAdapter
{
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().csrf().disable().authorizeRequests().antMatchers("/api/api-doc").permitAll()
        .antMatchers("/**").authenticated();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user").password("{noop}user").roles("USER", "ACTUATOR")
        .and()
        .withUser("api").password("{noop}api").roles("API");
  }
}