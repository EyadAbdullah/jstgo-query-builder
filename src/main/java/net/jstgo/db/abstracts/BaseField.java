package net.jstgo.db.abstracts;


import net.jstgo.db.enums.FunctionOperator;

public abstract class BaseField {
  protected String fieldAs;
  protected FunctionOperator functionOperator = FunctionOperator.NONE;

  protected String getFieldAs() {
    return fieldAs;
  }

  protected FunctionOperator getFunctionOperator() {
    return functionOperator;
  }
}
