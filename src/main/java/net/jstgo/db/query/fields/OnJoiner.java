package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.BaseJoiner;
import net.jstgo.db.enums.TableJoinerType;

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
