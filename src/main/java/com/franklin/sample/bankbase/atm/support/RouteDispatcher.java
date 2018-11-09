package com.franklin.sample.bankbase.atm.support;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteDispatcher
{
  private final CamelContext camelContext;

  private final ProducerTemplate producerTemplate;

  @Autowired
  public RouteDispatcher(CamelContext camelContext, ProducerTemplate producerTemplate) {
    this.camelContext = camelContext;
    this.producerTemplate = producerTemplate;
  }

  public <T> List<T> exchangeForList(String endpoint, Object body) throws Exception {
    final Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(body).build();
    final Exchange responseExchange = producerTemplate.send(endpoint, requestExchange);
    if (responseExchange.getException() != null) {
      throw responseExchange.getException();
    }
    Class<List<T>> clazz = CastSupport.cast(List.class);
    return clazz.cast(responseExchange.getOut().getBody(clazz));
  }
}
