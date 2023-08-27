package net.jstgo.db.query.fields;

import net.jstgo.db.abstracts.BaseField;
import net.jstgo.db.enums.AggregateFunction;
import net.jstgo.db.enums.FunctionOperator;

/**
 * @author : Eyad Abdullah
 * @version : 1.0 <br>
 *     <h3>{@link #aggregateFunction}: <br>
 *     - Example [{@link #aggregateFunction}=ADD] : add(value1,value2 ...) <br>
 *     - Example [{@link #aggregateFunction}=COUNT] : COUNT(value1,value2 ...) <br>
 */
public class FieldFunction extends BaseField {
  private final AggregateFunction aggregateFunction;
  private BaseField[] values;

  public FieldFunction(AggregateFunction aggregateFunction) {
    this.aggregateFunction = aggregateFunction;
  }

  public FieldFunction(AggregateFunction aggregateFunction, BaseField... values) {
    this.aggregateFunction = aggregateFunction;
    this.values = values;
  }

  public FieldFunction(
      AggregateFunction aggregateFunction, FunctionOperator functionOperator, BaseField... values) {
    this.aggregateFunction = aggregateFunction;
    this.values = values;
    this.functionOperator = functionOperator;
  }

  public AggregateFunction getAggregateFunction() {
    return aggregateFunction;
  }

  public BaseField[] getValues() {
    return values;
  }
}
