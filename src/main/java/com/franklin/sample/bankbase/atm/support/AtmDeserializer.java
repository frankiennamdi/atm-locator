package com.franklin.sample.bankbase.atm.support;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.franklin.sample.bankbase.atm.service.ATMInfo;

import java.io.IOException;

public class AtmDeserializer
    extends StdDeserializer<ATMInfo>
{

  public AtmDeserializer() {
    this(null);
  }

  public AtmDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public ATMInfo deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException
  {
    JsonNode atmNode = jp.getCodec().readTree(jp);
    return new ATMInfo(
            atmNode.get("address").get("street").textValue(),
            atmNode.get("address").get("housenumber").textValue(),
            atmNode.get("address").get("postalcode").textValue(),
            atmNode.get("address").get("city").textValue(),
            atmNode.get("address").get("geoLocation").get("lat").textValue(),
            atmNode.get("address").get("geoLocation").get("lng").textValue(),
            atmNode.get("distance").intValue(),
            atmNode.get("type").textValue());
  }
}