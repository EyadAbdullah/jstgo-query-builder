package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.abstracts.BaseJoiner;

public class TableJoiner extends BaseField {
  private final BaseJoiner[] joins;

  public TableJoiner(BaseJoiner[] joins) {
    this.joins = joins;
  }

  public BaseJoiner[] getJoins() {
    return joins;
  }
}
