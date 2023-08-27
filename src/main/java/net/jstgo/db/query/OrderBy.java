package net.jstgo.db.query;

/**
 * @author : Eyad Abdullah
 * @version : 1.0 <br>
 */
public class OrderBy {
  private final OrderByItem[] orders;

  public OrderBy(OrderByItem[] orders) {
    this.orders = orders;
  }

  public OrderByItem[] getOrders() {
    return orders;
  }
}
