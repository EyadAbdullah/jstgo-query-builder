package net.jstgo.db.abstracts;


import net.jstgo.db.enums.FunctionOperator;

/**
 * @author : Eyad Abdullah
 * @version : 1.0 <br>
 *     <h3>{@link #fieldAs}: <br>
 *     - Example [{@link #fieldAs}='someValue'] : 'ClassValue' AS someValue
 *     <h3>{@link #functionOperator}: <br>
 *     - Example [{@link #functionOperator}=ADD] : 'ClassValue' + <br>
 *     - Example [{@link #functionOperator}=SUB] : 'ClassValue' - <br>
 */
public abstract class BaseField {
  protected String fieldAs;
  protected FunctionOperator functionOperator = FunctionOperator.NONE;

  protected String getFieldAs() {
    return fieldAs;
  }

  protected FunctionOperator getFunctionOperator() {
    return functionOperator;
  }
}
