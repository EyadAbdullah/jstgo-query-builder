package net.jstgo.db.query;

public class OrderBy {
  private final OrderByItem[] orders;

  public OrderBy(OrderByItem[] orders) {
    this.orders = orders;
  }

  public OrderByItem[] getOrders() {
    return orders;
  }
}
