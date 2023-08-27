package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.DmlBuilder;
import java.util.LinkedHashSet;

public class Delete extends DmlBuilder {
  private From from;
  private Where where;
  private LinkedHashSet<Object> values = new LinkedHashSet<>();

  public Delete from(BaseField table) {
    return from(table, null);
  }

  public Delete from(BaseField table, BaseField joiners) {
    this.from = new From(table, joiners);
    return this;
  }

  public Delete where(BaseField... conditions) {
    this.where = new Where(conditions);
    return this;
  }

  public From getFrom() {
    return from;
  }

  public Where getWhere() {
    return where;
  }

  public Delete addCondition(BaseField condition) {
    if (this.where == null) {
      this.where = new Where(new BaseField[]{condition});
    } else {
      this.where.addCondition(condition);
    }
    return this;
  }

  public Delete addCondition(BaseField condition, Object value) {
    if (this.where == null) {
      this.where = new Where(new BaseField[]{condition});
    } else {
      this.where.addCondition(condition);
    }
    this.values.add(value);
    return this;
  }

  public LinkedHashSet<Object> getValues() {
    return values;
  }
}
