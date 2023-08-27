package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.query.Select;

public class FieldQuery extends BaseField {
  private final Select query;

  public FieldQuery(Select query) {
    this.query = query;
  }

  public Select getQuery() {
    return query;
  }
}
