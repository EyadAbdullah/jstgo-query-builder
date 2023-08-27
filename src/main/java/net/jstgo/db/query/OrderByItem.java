package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.enums.OrderByType;

/**
 * @author : Eyad Abdullah
 * @version : 1.0 <br>
 */
public class OrderByItem extends BaseField {
  private final BaseField field;
  private final OrderByType type;

  public OrderByItem(BaseField field, OrderByType type) {
    this.field = field;
    this.type = type;
  }

  public BaseField getField() {
    return field;
  }

  public OrderByType getType() {
    return type;
  }
}
