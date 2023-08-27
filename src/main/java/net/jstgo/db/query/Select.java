package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.DmlBuilder;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class Select extends DmlBuilder {

  private LinkedHashSet<BaseField> fields;
  private From from;
  private Where where;
  private GroupBy groupBy;
  private OrderBy orderBy;
  private int limit = 15000;
  private boolean distinct;
  private LinkedHashSet<Object> values = new LinkedHashSet<>();

  public Select select(BaseField... fields) {
    this.fields = new LinkedHashSet<>(Arrays.asList(fields));
    return this;
  }

  public Select addField(BaseField field) {
    this.fields.add(field);
    return this;
  }

  public Select distinct(boolean distinct) {
    this.distinct = distinct;
    return this;
  }

  public Select from(BaseField table) {
    return from(table, null);
  }

  public Select from(BaseField table, BaseField joiners) {
    this.from = new From(table, joiners);
    return this;
  }

  public Select where(BaseField... conditions) {
    this.where = new Where(conditions);
    return this;
  }

  public Select addCondition(BaseField condition) {
    if (this.where == null) {
      this.where = new Where(new BaseField[]{condition});
    } else {
      this.where.addCondition(condition);
    }
    return this;
  }

  public Select addCondition(BaseField condition, Object value) {
    if (this.where == null) {
      this.where = new Where(new BaseField[]{condition});
    } else {
      this.where.addCondition(condition);
    }
    this.values.add(value);
    return this;
  }

  public Select groupBy(BaseField... groupByItems) {
    this.groupBy = new GroupBy(groupByItems);
    return this;
  }

  public Select orderBy(OrderByItem... orderByItems) {
    this.orderBy = new OrderBy(orderByItems);
    return this;
  }

  public Select limit(int limit) {
    this.limit = limit;
    return this;
  }

  public BaseField[] getFields() {
    return fields.toArray(new BaseField[0]);
  }

  public From getFrom() {
    return from;
  }

  public Where getWhere() {
    return where;
  }

  public GroupBy getGroupBy() {
    return groupBy;
  }

  public OrderBy getOrderBy() {
    return orderBy;
  }

  public int getLimit() {
    return limit;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public LinkedHashSet<Object> getValues() {
    return values;
  }
}
