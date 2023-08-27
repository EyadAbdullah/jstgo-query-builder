package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.BaseJoiner;

/**
 * @author : Eyad Abdullah
 * @version : 1.0 <br>
 *     <h3>Description : for Joining Table with other tables
 *     <h3>{@link #joins} : List of joins. <br>
 */
public class TableJoiner extends BaseField {
  private final BaseJoiner[] joins;

  public TableJoiner(BaseJoiner[] joins) {
    this.joins = joins;
  }

  public BaseJoiner[] getJoins() {
    return joins;
  }
}
