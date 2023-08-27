package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.enums.FunctionOperator;

public class FieldAnonymous extends BaseField {

  public FieldAnonymous(FunctionOperator operator) {
    this.functionOperator = operator;
  }
}
