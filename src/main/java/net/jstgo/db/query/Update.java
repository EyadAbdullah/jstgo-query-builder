package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.DmlBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class Update extends DmlBuilder {

  private final BaseField table;
  private List<BaseField> fields = new ArrayList<>();
  private LinkedHashSet<Object> values = new LinkedHashSet<>();
  private Where where;

  public Update(BaseField table) {
    this.table = table;
  }

  public Update fields(BaseField... fields) {
    this.fields = new ArrayList<>(Arrays.asList(fields));
    return this;
  }

  public Update field(BaseField field) {
    this.fields.add(field);
    return this;
  }

  public Update field(BaseField field, Object value) {
    this.fields.add(field);
    this.values.add(value);
    return this;
  }

  public Update where(BaseField... conditions) {
    this.where = new Where(conditions);
    return this;
  }

  public BaseField getTable() {
    return table;
  }

  public BaseField[] getFields() {
    return fields.toArray(new BaseField[0]);
  }


  public Where getWhere() {
    return where;
  }

  public Update addCondition(BaseField condition) {
    if (this.where == null) {
      this.where = new Where(new BaseField[]{condition});
    } else {
      this.where.addCondition(condition);
    }
    return this;
  }

  public Update addCondition(BaseField condition, Object value) {
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
