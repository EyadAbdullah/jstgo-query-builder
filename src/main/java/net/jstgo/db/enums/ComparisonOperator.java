package net.jstgo.db.enums;

public enum ComparisonOperator {
  IS_NULL,
  IS_NOT_NULL,
  EQUAL,
  NOT_EQUAL,
  GREATER,
  GREATER_OR_EQUAL,
  LESS,
  LESS_OR_EQUAL,
  BETWEEN,
  LIKE,
  IN;

  public static ComparisonOperator getValue(String str) {
    for (var val : values()) {
      if (val.name().equalsIgnoreCase(str)) {
        return val;
      }
    }
    throw new IllegalStateException("Unsupported type : " + str);
  }
}
