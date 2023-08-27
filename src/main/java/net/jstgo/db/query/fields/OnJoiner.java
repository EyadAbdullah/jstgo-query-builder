package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.BaseJoiner;
import net.jstgo.db.enums.TableJoinerType;

/**
 * @author : Eyad Abdullah
 * @version : 1.0 <br>
 *     <h3>Description : join item for {@link TableJoiner}
 *     <h3>{@link #type} : join type <br>
 *     <h3>{@link #table} : join table name <br>
 *     <h3>{@link #field} : join table field <br>
 *     <br>
 *     Example: {@link #type} JOIN {@link #table} ON {@link #field}={@link #field};
 */
public class OnJoiner extends BaseJoiner {
  private final TableJoinerType type;
  private final BaseField table;
  private final BaseField field;

  public OnJoiner(TableJoinerType type, BaseField table, BaseField field) {
    this.type = type;
    this.table = table;
    this.field = field;
  }

  public TableJoinerType getType() {
    return type;
  }

  public BaseField getTable() {
    return table;
  }

  public BaseField getField() {
    return field;
  }
}
