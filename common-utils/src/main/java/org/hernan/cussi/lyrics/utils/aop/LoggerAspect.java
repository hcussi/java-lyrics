package org.hernan.cussi.lyrics.utils.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class LoggerAspect {

  private static final Map<Class<?>, Logger> LOGGER_MAP = new HashMap<>();

  protected static synchronized void log(final Class<?> clazz, final String message) {
    getLog(clazz).info(message);
  }

  protected static synchronized void log(final Class<?> clazz, final Object[] args, final String signatureName) {
    getLog(clazz).info(STR."Method called: \{signatureName}" + (hasArgs(args) ? STR." called with: \{args[0]}" : ""));
  }

  protected static synchronized void log(final Class<?> clazz, final Object[] args, final String signatureName, final Throwable exception) {
    getLog(clazz).error(STR."Method error: \{signatureName}" + (hasArgs(args) ? STR." called with: \{args[0]}" : ""), exception);
  }

  private static Logger getLog(final Class<?> clazz) {
    return LOGGER_MAP.computeIfAbsent(clazz, (_) -> LoggerFactory.getLogger(clazz));
  }

  private static boolean hasArgs(final Object[] args) {
    return args.length > 0 && args[0] != null;
  }

}
