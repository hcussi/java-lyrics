package org.hernan.cussi.lyrics.userservice.aop;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class LoggerAspect {

  private static final Map<Class<?>, Logger> LOGGER_MAP = new HashMap<>();

  protected static synchronized Logger getLogger(JoinPoint joinPoint) {
    final Class<?> clazz = joinPoint.getTarget().getClass();
    return LOGGER_MAP.computeIfAbsent(clazz, (_) -> LoggerFactory.getLogger(clazz));
  }

}
