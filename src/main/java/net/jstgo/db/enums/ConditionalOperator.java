package net.jstgo.db.enums;

public enum ConditionalOperator {
  AND,
  NOT,
  OR;

  public static ConditionalOperator getValue(String str) {
    for (var val : values()) {
      if (val.name().equalsIgnoreCase(str)) {
        return val;
      }
    }
    throw new IllegalStateException("Unsupported type : " + str);
  }
}