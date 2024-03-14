package org.hernan.cussi.lyrics.utils.telemetry;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationFilter;
import io.micrometer.tracing.exporter.SpanExportingPredicate;

public class TelemetryUtils {

  // https://stackoverflow.com/questions/75143863/how-to-exclude-some-uri-to-be-observed-using-springboot3-micrometer
  // https://github.com/spring-projects/spring-boot/issues/34400#issuecomment-1460792106

  // This adds the http.url keyvalue to security observations from the root (mvc) observation
  // You add an ignoreSpan=true keyValue instead if you want, or something that can signal to the SpanExportingPredicate what to ignore
  public static ObservationFilter urlObservationFilter() {
    return (context) -> {
      if (context.getName().startsWith("spring.security.")) {
        Observation.Context root = getRoot(context);
        if (root.getName().equals("http.server.requests")) {
          context.addHighCardinalityKeyValue(root.getHighCardinalityKeyValue("http.url"));
        }
      }

      return context;
    };
  }

  // This ignores actuator spans but its logic depends on the ObservationFilter above
  // Auto-configuration for SpanExportingPredicate was added in 3.1.0-M1
  // So either you use 3.1.x or you can add the same to your config : https://github.com/spring-projects/spring-boot/pull/34002
  public static SpanExportingPredicate actuatorSpanExportingPredicate() {
    return (span) -> {
      var tagValueUrl = span.getTags().getOrDefault("http.url", "");
      var tagValueUri = span.getTags().getOrDefault("http.uri", "");
      return !(tagValueUrl.startsWith("/actuator") || tagValueUri.startsWith("/actuator"));
    };
  }

  private static Observation.Context getRoot(Observation.Context context) {
    if (context.getParentObservation() == null) {
      return context;
    }
    else {
      return getRoot((Observation.Context) context.getParentObservation().getContextView());
    }
  }

}
