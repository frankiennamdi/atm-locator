package com.franklin.sample.bankbase.atm.support;

public class CastSupport
{
  @SuppressWarnings("unchecked")
  public static <T> T cast(final Object object) {
    return (T) object;
  }
}
