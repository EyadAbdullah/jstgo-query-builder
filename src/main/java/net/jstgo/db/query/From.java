package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.query.fields.FieldQuery;
import net.jstgo.db.query.fields.FieldValue;
import net.jstgo.db.query.fields.TableJoiner;

/**
 * @author : Eyad Abdullah
 * @version : 1.0 *
 *     <h3>{@link #table}: can be:<br>
 *     <ul>
 *       <li>{@link TableJoiner}
 *       <li>{@link FieldQuery}
 *       <li>{@link FieldValue}
 *     </ul>
 */
public class From {
  private final BaseField table;
  private final BaseField joiners;

  public From(BaseField table, BaseField joiners) {
    this.table = table;
    this.joiners = joiners;
  }

  public BaseField getTable() {
    return table;
  }

  public BaseField getJoiners() {
    return joiners;
  }
}
