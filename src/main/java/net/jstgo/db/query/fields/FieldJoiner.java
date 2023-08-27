package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;

public class FieldJoiner extends BaseField {
  private final BaseField[] fields;
  private final boolean parentheses;

  public FieldJoiner(BaseField[] fields, boolean parentheses) {
    this.fields = fields;
    this.parentheses = parentheses;
  }

  public boolean isParentheses() {
    return parentheses;
  }

  public BaseField[] getFields() {
    return fields;
  }
}
