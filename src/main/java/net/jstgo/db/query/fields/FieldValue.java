package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.enums.ComparisonOperator;
import net.jstgo.db.enums.ConditionalOperator;

public class FieldValue extends BaseField implements IsCondition {

  private final String tableName;
  private final String name;
  private final ComparisonOperator comparisonOperator;
  private final ConditionalOperator conditionalOperator;
  private final BaseField[] values;

  public FieldValue(String tableName, String name, String asField,
      ComparisonOperator comparisonOperator,
      ConditionalOperator conditionalOperator,
      BaseField[] values) {
    this.tableName = tableName;
    this.name = name;
    this.comparisonOperator = comparisonOperator;
    this.conditionalOperator = conditionalOperator;
    this.values = values;
    this.fieldAs = asField;
  }

  public String getTableName() {
    return tableName;
  }

  public String getName() {
    return name;
  }

  public ComparisonOperator getComparisonOperator() {
    return comparisonOperator;
  }

  @Override
  public ConditionalOperator getConditionalOperator() {
    return conditionalOperator;
  }

  public BaseField[] getValues() {
    return values;
  }
}
