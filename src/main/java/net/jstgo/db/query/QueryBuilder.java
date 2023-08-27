package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.BaseJoiner;
import net.jstgo.db.enums.AggregateFunction;
import net.jstgo.db.enums.ComparisonOperator;
import net.jstgo.db.enums.ConditionalOperator;
import net.jstgo.db.enums.FunctionOperator;
import net.jstgo.db.enums.OrderByType;
import net.jstgo.db.enums.TableJoinerType;
import net.jstgo.db.query.fields.FieldAnonymous;
import net.jstgo.db.query.fields.FieldFunction;
import net.jstgo.db.query.fields.FieldJoiner;
import net.jstgo.db.query.fields.FieldObject;
import net.jstgo.db.query.fields.FieldQuery;
import net.jstgo.db.query.fields.FieldValue;
import net.jstgo.db.query.fields.OnJoiner;
import net.jstgo.db.query.fields.TableJoiner;

public class QueryBuilder {

  public static Select newSelect() {
    return new Select();
  }

  public static Update newUpdate(BaseField table) {
    return new Update(table);
  }

  public static Insert newInsert() {
    return new Insert();
  }

  public static Delete newDelete() {
    return new Delete();
  }

  // region builder functions

  public FieldValue field(String value) {
    return new FieldValue(value, null, null, null, null, null);
  }

  public FieldValue fieldAs(String value, String asField) {
    return new FieldValue(value, null, asField, null, null, null);
  }

  public FieldValue field(
      String tableName,
      String fieldName,
      ComparisonOperator comparisonOperator,
      BaseField... values) {
    return new FieldValue(tableName, fieldName, null, comparisonOperator, null, values);
  }

  public FieldValue field(String fieldName, BaseField... values) {
    return new FieldValue(null, fieldName, null, ComparisonOperator.EQUAL, null, values);
  }

  public FieldValue field(String tableName, String fieldName, BaseField... values) {
    return new FieldValue(tableName, fieldName, null, ComparisonOperator.EQUAL, null, values);
  }

  public FieldValue[] fields(String... values) {
    var fields = new FieldValue[values.length];
    for (var i = 0; i < values.length; i++) {
      fields[i] = new FieldValue(values[i], null, null, null, null, null);
    }
    return fields;
  }

  public FieldObject value(Object object) {
    return value(object, null);
  }

  public FieldAnonymous value() {
    return new FieldAnonymous(null);
  }

  public FieldObject value(Object object, FunctionOperator operator) {
    return new FieldObject(object, operator);
  }

  public FieldValue condition(
      String tableName, String fieldName, ComparisonOperator operator, BaseField value) {
    return new FieldValue(
        tableName, fieldName, null, operator, ConditionalOperator.AND, new BaseField[] {value});
  }

  public FieldValue condition(
      String tableName,
      String fieldName,
      ComparisonOperator operator,
      ConditionalOperator conditionalOperator,
      BaseField value) {
    return new FieldValue(
        tableName, fieldName, null, operator, conditionalOperator, new BaseField[] {value});
  }

  public FieldValue condition(
      String tableName,
      String fieldName,
      ComparisonOperator comparisonOperator,
      ConditionalOperator conditionalOperator,
      BaseField... values) {
    return new FieldValue(
        tableName, fieldName, null, comparisonOperator, conditionalOperator, values);
  }

  public FieldFunction function(AggregateFunction function) {
    return new FieldFunction(function);
  }

  public FieldFunction function(AggregateFunction function, BaseField... values) {
    return new FieldFunction(function, values);
  }

  public FieldQuery query(Select select) {
    return new FieldQuery(select);
  }

  public FieldJoiner fieldJoiner(BaseField... values) {
    return fieldJoiner(true, values);
  }

  public FieldJoiner fieldJoiner(boolean parentheses, BaseField... values) {
    return new FieldJoiner(values, parentheses);
  }

  public OnJoiner onJoiner(TableJoinerType type, BaseField table, BaseField field) {
    return new OnJoiner(type, table, field);
  }

  public TableJoiner tableJoiner(BaseJoiner... joiners) {
    return new TableJoiner(joiners);
  }

  public OrderByItem order(BaseField field, OrderByType order) {
    return new OrderByItem(field, order);
  }
  // endregion
}
