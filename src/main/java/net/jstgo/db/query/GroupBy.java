package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;

/**
 * @author : Eyad Abdullah
 * @version : 1.0 <br>
 */
public class GroupBy {
  private final BaseField[] fields;

  public GroupBy(BaseField[] fields) {
    this.fields = fields;
  }

  public BaseField[] getFields() {
    return fields;
  }
}
