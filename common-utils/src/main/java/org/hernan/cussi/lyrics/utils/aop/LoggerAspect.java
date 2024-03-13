package org.hernan.cussi.lyrics.utils.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class LoggerAspect {

  private static final Map<Class<?>, Logger> LOGGER_MAP = new HashMap<>();

  protected static synchronized void log(final Class<?> clazz, final Object[] args, final String signatureName) {
    var log =  LOGGER_MAP.computeIfAbsent(clazz, (_) -> LoggerFactory.getLogger(clazz));

    var hasArgs = args.length > 0 && args[0] != null;

    if (log.isInfoEnabled()) {
      log.info(STR."REST method: \{signatureName}" + (hasArgs ? STR." called with: \{args[0]}" : ""));
    }
  }

}
