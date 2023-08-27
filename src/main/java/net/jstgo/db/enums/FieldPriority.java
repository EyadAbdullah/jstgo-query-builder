package net.jstgo.db.enums;

public enum FieldPriority
{
  FIRST(-1), MIDDLE(0), LAST(1);
  private final int value;

  FieldPriority(int value){
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
