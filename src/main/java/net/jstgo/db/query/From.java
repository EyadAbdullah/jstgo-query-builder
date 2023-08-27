package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;

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
