package org.hernan.cussi.lyrics.utils.message;

import java.util.HashMap;
import java.util.Map;

public enum BindingNames {

   USER_CREATED("userCreated-out-0");

  private static final Map<String, BindingNames> BY_LABEL = new HashMap<>();

  static {
    for (BindingNames e: values()) {
      BY_LABEL.put(e.label, e);
    }
  }

  public final String label;

  BindingNames(final String label) {
    this.label = label;
  }

  public static BindingNames valueOfLabel(final String label) {
    return BY_LABEL.get(label);
  }
}
