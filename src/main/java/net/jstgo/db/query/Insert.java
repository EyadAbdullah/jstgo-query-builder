package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.DmlBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Insert extends DmlBuilder {
  private BaseField table;
  private List<BaseField> fields = new ArrayList<>();
  private List<BaseField[]> values = new ArrayList<>();
  private Select select;

  public Insert insert(BaseField table) {
    this.table = table;
    return this;
  }

  public Insert fields(BaseField... fields) {
    this.fields = new ArrayList<>(Arrays.asList(fields));
    return this;
  }

  public Insert field(BaseField field, BaseField... value) {
    this.fields.add(field);
    this.values.add(value);
    return this;
  }

  public Insert addField(BaseField field) {
    this.fields.add(field);
    return this;
  }

  public Insert addValue(BaseField... values) {
    this.values.add(values);
    return this;
  }

  public Insert values(List<BaseField[]> values) {
    this.values = values;
    return this;
  }

  public Insert values(Select select) {
    this.select = select;
    return this;
  }

  public BaseField getTable() {
    return table;
  }

  public BaseField[] getFields() {
    return fields.toArray(new BaseField[0]);
  }

  public List<BaseField[]> getValues() {
    return values;
  }

  public Select getSelect() {
    return select;
  }
}
