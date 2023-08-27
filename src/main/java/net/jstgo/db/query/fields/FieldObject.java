package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.enums.FunctionOperator;

public class FieldObject extends BaseField {
  private final Object value;

  public FieldObject(Object value, FunctionOperator operator) {
    this.value = value;
    this.functionOperator = operator;
  }

  public Object getValue() {
    return value;
  }
}
