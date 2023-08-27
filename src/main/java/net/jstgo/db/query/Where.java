package net.jstgo.db.query;

import net.jstgo.db.abstracts.BaseField;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class Where {

  private final LinkedHashSet<BaseField> conditions;

  public Where(BaseField[] conditions) {
    this.conditions = new LinkedHashSet<>(Arrays.asList(conditions));
  }

  public Where(LinkedHashSet<BaseField> conditions) {
    this.conditions = conditions;
  }

  public BaseField[] getConditions() {
    return conditions.toArray(new BaseField[0]);
  }

  public void addCondition(BaseField condition) {
    conditions.add(condition);
  }
}
