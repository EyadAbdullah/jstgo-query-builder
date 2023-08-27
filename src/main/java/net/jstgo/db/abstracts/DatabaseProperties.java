package net.jstgo.db.abstracts;

import net.jstgo.db.query.Delete;
import net.jstgo.db.query.From;
import net.jstgo.db.query.GroupBy;
import net.jstgo.db.query.Insert;
import net.jstgo.db.query.OrderBy;
import net.jstgo.db.query.OrderByItem;
import net.jstgo.db.query.Select;
import net.jstgo.db.query.Update;
import net.jstgo.db.query.Where;
import net.jstgo.db.query.fields.FieldAnonymous;
import net.jstgo.db.query.fields.FieldFunction;
import net.jstgo.db.query.fields.FieldJoiner;
import net.jstgo.db.query.fields.FieldObject;
import net.jstgo.db.query.fields.FieldQuery;
import net.jstgo.db.query.fields.FieldValue;
import net.jstgo.db.query.fields.IsCondition;
import net.jstgo.db.query.fields.OnJoiner;
import net.jstgo.db.query.fields.TableJoiner;
import net.jstgo.db.config.FieldConfig;
import net.jstgo.db.config.ModuleConfig;
import net.jstgo.db.enums.ComparisonOperator;
import net.jstgo.db.enums.ConditionalOperator;
import net.jstgo.db.enums.FunctionOperator;
import net.jstgo.db.enums.OrderByType;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;

public abstract class DatabaseProperties implements IDatabaseProperties {

  // region constants
  // --- TABLES
  public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS";
  public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS";
  public static final String SUPPORT_CASCADE = "ON UPDATE CASCADE ON DELETE CASCADE";

  // --- IMPORTANT
  public static final String SELECT = "SELECT";
  public static final String UPDATE = "UPDATE";
  public static final String CREATE = "CREATE";
  public static final String DELETE = "DELETE";
  public static final String INSERT_INTO = "INSERT INTO";

  // joiners
  public static final String JOIN = "JOIN";
  public static final String LEFT = "LEFT";
  public static final String RIGHT = "RIGHT";
  public static final String INNER = "INNER";
  public static final String FULL = "FULL";

  // --- OPERATORS
  public static final String AND = "AND";
  public static final String COUNT = "COUNT";
  public static final String OR = "OR";
  public static final String NOT = "NOT";

  // --- WHERE OPERATORS
  public static final String EQUAL = "=";
  public static final String NOT_EQUAL = "!=";
  public static final String GREATER = ">";
  public static final String GREATER_OR_EQUAL = ">=";
  public static final String LESS = "<";
  public static final String LESS_OR_EQUAL = "<=";
  public static final String BETWEEN = "BETWEEN";
  public static final String LIKE = "LIKE";
  public static final String IN = "IN";

  public static final String FROM = "FROM";
  public static final String WHERE = "WHERE";
  public static final String VALUES = "VALUES";
  public static final String GROUP_BY = "GROUP BY";
  public static final String ORDER_BY = "ORDER BY";
  public static final String ASC = "ASC";
  public static final String DESC = "DESC";
  public static final String LIMIT = "LIMIT";
  public static final String TOP = "TOP";
  public static final String SET = "SET";
  public static final String PERCENT = "PERCENT";
  public static final String DISTINCT = "DISTINCT";
  public static final String EXISTS = "EXISTS";

  // --- FOR FUNCTIONS
  public static final String NULL = "NULL";
  public static final String NOT_NULL = "NOT NULL";
  public static final String IS_NULL = "IS NULL";
  public static final String IS_NOT_NULL = "IS NOT NULL";
  public static final String MIN = "MIN";
  public static final String MAX = "MAX";

  // Math
  public static final String M_ADD = "+";
  public static final String M_SUB = "-";
  public static final String M_MUL = "*";
  public static final String M_DIV = "/";

  // GO TYPES
  public static final String GO_VARCHAR = "VARCHAR";
  public static final String GO_INTEGER = "INT";
  public static final String GO_FLOAT = "FLOAT";
  public static final String GO_DATE = "DATE";
  public static final String GO_BOOLEAN = "BOOLEAN";
  public static final String GO_LONG = "LONG";
  public static final String GO_TIMESTAMP = "TIMESTAMP";

  public static final String ON = "ON";
  public static final String ADD = "ADD";
  public static final String UNIQUE = "UNIQUE";
  public static final String DEFAULT = "DEFAULT";
  public static final String CONSTRAINT = "CONSTRAINT";
  public static final String REFERENCES = "REFERENCES";
  public static final String PRIMARY_KEY = "PRIMARY KEY";
  public static final String FOREIGN_KEY = "FOREIGN KEY";

  public static final String ASTERISKS = "*";
  public static final String COMMA = ",";
  public static final String SPACE = " ";
  public static final String DOT = ".";
  public static final String SEMICOLON = ";";
  public static final String OPEN_BACK_QUTE = "OPEN_BACK_QUTE"; // change
  public static final String CLOSE_BACK_QUTE = "CLOSE_BACK_QUTE"; // change
  public static final String OPEN_QUOTE = "'"; // change
  public static final String CLOSE_QUOTE = "'"; // change
  public static final String OPEN_PARENTHESIS = "(";
  public static final String CLOSE_PARENTHESIS = ")";
  public static final String QUESTION_MARK = "?";
  public static final String NEW_LINE = "\n";

  // endregion

  private final Map<String, String> keywords = new HashMap<>();

  // region constructor

  protected DatabaseProperties(Map<String, String> newKeywords) {
    this();
    newKeywords.forEach(keywords::replace);
  }

  protected DatabaseProperties() {
    Arrays.stream(this.getClass().getSuperclass().getDeclaredFields())
        .forEach(
            field -> {
              if (Modifier.isPublic(field.getModifiers())
                  && Modifier.isStatic(field.getModifiers())
                  && Modifier.isFinal(field.getModifiers())) {
                try {
                  keywords.putIfAbsent(field.get(this).toString(), field.get(this).toString());
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                }
              }
            });
  }

  // endregion

  // region Table Builder

  protected String handleDefaultTableQuery(ModuleConfig module) {
    var result = new StringBuilder();
    if (module == null) {
      return "";
    }
    if (StringUtils.isNotBlank(module.getName())) {
      result.append(ws(CREATE_TABLE)).append(backQuote(module.getName()));
    }
    var fields = new StringJoiner(ws(COMMA));
    // ignore list field where these are handled in the ConfigurationsManager
    module.getFields().stream()
        .filter(FieldConfig::isNative)
        .filter(field -> !handleTableFieldQuery(field).isEmpty())
        .forEach(field -> fields.add(handleTableFieldQuery(field)));

    return trimQuery(result.append(parenthesis(fields.toString())).toString());
  }

  protected String handleTableFieldQuery(FieldConfig field) {
    var result = new StringBuilder();
    if (field == null || field.getFieldType() == null) {
      return "";
    }
    result.append(spacing(backQuote(field.getName())));
    switch (field.getFieldType()) {
      case VARCHAR:
        result.append(ws(GO_VARCHAR)).append(parenthesis(String.valueOf(field.getLength())));
        break;
      case INTEGER, LONG:
        result.append(ws(GO_INTEGER)).append(parenthesis(String.valueOf(field.getLength())));
        break;
      case BOOLEAN:
        result.append(ws(GO_BOOLEAN));
        break;
      case FLOAT:
        result.append(ws(GO_FLOAT));
        break;
      case DATE:
        result.append(ws(GO_DATE));
        break;
      case TIMESTAMP:
        result.append(ws(GO_TIMESTAMP));
        break;
      default:
        return "";
    }
    // set nullable
    result.append(field.isNotNull() ? ws(NOT_NULL) : ws(NULL));

    // set Primary Key
    if (field.isPrimaryKey()) {
      result.append(ws(PRIMARY_KEY));
    } else {
      // set Unique
      result.append(field.isUnique() ? ws(UNIQUE) : "");
    }

    // set Default
    if (StringUtils.isNotBlank(field.getDefault())) {
      result.append(handleFieldDefault(field));
    }

    // set Foreign
    if (field.getForeign() != null) {
      // set Cascade
      var cascade = field.getForeign().isSupportCascade() ? ws(SUPPORT_CASCADE) : "";
      result
          .append(ws(COMMA))
          .append(ws(NEW_LINE))
          .append(ws(CONSTRAINT))
          .append(spacing(backQuote(field.getParentModule().getName() + "_" + field.getName())))
          .append(ws(FOREIGN_KEY))
          .append(parenthesis(backQuote(field.getName())))
          .append(ws(REFERENCES))
          .append(backQuote(field.getForeign().getTableReference()))
          .append(parenthesis(backQuote(field.getForeign().getFieldReference())))
          .append(cascade);
    }
    return result.toString();
  }

  protected String handleFieldDefault(FieldConfig field) {
    var result = new StringBuilder();
    if (StringUtils.isBlank(field.getDefault())) {
      return result.toString();
    }
    switch (field.getFieldType()) {
      case BOOLEAN -> result.append(ws(DEFAULT))
          .append(handleFieldDefaultBoolean(field.getDefault()));
      default -> result.append(ws(DEFAULT)).append(quote(field.getDefault()));
    }
    return result.toString();
  }

  protected String handleFieldDefaultBoolean(String fieldDefault) {
    if (fieldDefault.equalsIgnoreCase("true")
        || fieldDefault.equalsIgnoreCase("1")) {
      return "1";
    }
    return "0";
  }

  // endregion

  // region Query Builder

  protected String handleSelect(Select select) {
    return ws(SELECT)
        + handleDistinct(select.isDistinct())
        + handleLimit(select.getLimit())
        + handleBaseFields(select.getFields())
        + handleFrom(select.getFrom())
        + handleWhere(select.getWhere())
        + handleGroupBy(select.getGroupBy())
        + handleOrderBy(select.getOrderBy());
  }

  protected String handleInsert(Insert insert) {
    if (insert != null) {
      var result = new StringBuilder(ws(INSERT_INTO));
      if (insert.getTable() != null && insert.getFields() != null) {
        result
            .append(handleBaseField(insert.getTable()))
            .append(parenthesis(handleBaseFields(insert.getFields())));
      }
      if (!insert.getValues().isEmpty()) {
        result.append(ws(VALUES));
        var joiner = new StringJoiner(ws(COMMA));
        for (var x = 0; x < insert.getValues().size(); x++) {
          joiner.add(handleBaseFields(insert.getValues().get(x)));
        }
        result.append(parenthesis(joiner.toString()));
      } else if (insert.getSelect() != null) {
        result.append(spacing(handleSelect(insert.getSelect())));
      }
      return result.toString();
    }
    return "";
  }

  protected String handleUpdate(Update update) {
    if (update != null) {
      return ws(UPDATE) + handleBaseField(update.getTable()) +
          ws(SET) + handleBaseFields(update.getFields()) +
          handleWhere(update.getWhere());
    }
    return "";
  }

  protected String handleDelete(Delete delete) {
    if (delete != null) {
      return ws(DELETE) + handleFrom(delete.getFrom()) + handleWhere(delete.getWhere());
    }
    return "";
  }

  protected String handleDistinct(boolean distinct) {
    return distinct ? ws(DISTINCT) : "";
  }

  // table.field = 'value', table.field = 'value'
  protected String handleBaseFields(BaseField[] fields) {
    return handleBaseFields(fields, ws(COMMA));
  }

  protected String handleBaseFields(BaseField[] fields, String prefix) {
    var result = new StringBuilder();
    if (fields == null || fields.length == 0) {
      return ws(ASTERISKS);
    }
    for (var x = 0; x < fields.length; x++) {
      var field = fields[x];
      result.append(handleBaseField(field));
      if (x < fields.length - 1) {
        // add field FunctionOperator
        if (StringUtils.isNotBlank(prefix)) {
          result.append(prefix);
        } else {
          if (field instanceof IsCondition) {
            // add the ConditionalOperator for the field
            result.append(
                handleConditionalOperator(((IsCondition) field).getConditionalOperator()));
          } else {
            result.append(handleFunctionOperator(field.getFunctionOperator()));
          }
        }
      }
    }
    return result.toString();
  }

  // FROM table
  protected String handleFrom(From from) {
    var result = new StringBuilder();
    if (from != null) {
      result
          .append(ws(FROM))
          .append(handleBaseField(from.getTable()))
          .append(handleBaseField(from.getJoiners()));
    }
    return result.toString();
  }

  protected String handleOrderBy(OrderBy orderBy) {
    var result = new StringJoiner(ws(COMMA));
    if (orderBy != null && orderBy.getOrders() != null) {
      for (var order : orderBy.getOrders()) {
        result.add(handleOrderByItem(order));
      }
      if (!result.toString().isEmpty()) {
        return ws(ORDER_BY) + result;
      }
    }
    return "";
  }

  protected String handleGroupBy(GroupBy groupBy) {
    var result = new StringJoiner(ws(COMMA));
    if (groupBy != null && groupBy.getFields() != null) {
      for (var group : groupBy.getFields()) {
        result.add(handleBaseField(group));
      }
      if (!result.toString().isEmpty()) {
        return ws(GROUP_BY) + result;
      }
    }
    return "";
  }

  protected String handleOrderByItem(OrderByItem orderBy) {
    if (orderBy != null && orderBy.getType() == OrderByType.ASC) {
      return handleBaseField(orderBy.getField()) + ws(ASC);
    } else if (orderBy != null && orderBy.getType() == OrderByType.DESC) {
      return handleBaseField(orderBy.getField()) + ws(DESC);
    }
    return "";
  }

  // WHERE table.field = value AND table.field = value
  // WHERE table.field in (value1, value2)
  // WHERE Exist(query)
  protected String handleWhere(Where where) {
    var result = new StringBuilder();
    if (where != null) {
      var conditions = handleConditions(where.getConditions());
      if (StringUtils.isNotBlank(conditions)) {
        result.append(ws(WHERE)).append(conditions);
      }
    }
    return result.toString();
  }

  protected String handleLimit(int limit) {
    return ws(LIMIT) + limit;
  }

  // condition AND condition
  // table.field = value AND table.field = value
  protected String handleConditions(BaseField[] conditions) {
    var result = new StringBuilder();
    if (conditions != null) {
      for (var x = 0; x < conditions.length; x++) {
        var field = conditions[x];
        result.append(handleBaseField(field));
        if (field instanceof IsCondition && x < conditions.length - 1) {
          // add the ConditionalOperator for the field
          result.append(handleConditionalOperator(((IsCondition) field).getConditionalOperator()));
        }
      }
    }
    return result.toString();
  }

  // table.field = 'value'
  protected String handleBaseField(BaseField field) {
    var result = new StringBuilder();
    if (field instanceof FieldValue) {
      result.append(handleFieldValue((FieldValue) field));
    } else if (field instanceof FieldObject) {
      result.append(handleFieldObject((FieldObject) field));
    } else if (field instanceof FieldQuery) {
      result.append(handleSelect(((FieldQuery) field).getQuery()));
    } else if (field instanceof FieldFunction) {
      result.append(handleFieldFunction((FieldFunction) field));
    } else if (field instanceof FieldJoiner) {
      result.append(handleFieldJoiner((FieldJoiner) field));
    } else if (field instanceof TableJoiner) {
      result.append(handleTableJoiner((TableJoiner) field));
    } else if (field instanceof FieldAnonymous) {
      result.append(handleTableJoiner((FieldAnonymous) field));
    }
    return result.toString();
  }

  // table.field = 'value'
  protected String handleFieldValue(FieldValue field) {
    var result = new StringBuilder();
    if (field != null) {
      // table
      if (StringUtils.isNotBlank(field.getTableName())) {
        result.append(backQuote(field.getTableName()));
      }
      // table.field
      if (StringUtils.isNotBlank(field.getName()) && StringUtils.isNotBlank(field.getTableName())) {
        result.append(ws(DOT));
      }
      // field
      if (StringUtils.isNotBlank(field.getName())) {
        result.append(backQuote(field.getName()));
      }
      // table.field =
      // table.field in ()
      result.append(handleOperator(field.getComparisonOperator(), field.getValues()));

      if (StringUtils.isNotBlank(field.getFieldAs())) {
        result.append(spacing(field.getFieldAs()));
      }
    }
    return result.toString();
  }

  // 'value'
  // value
  protected String handleFieldObject(FieldObject field) {
    if (field.getValue() != null) {
      if (field.getValue() instanceof String) {
        return quote(String.valueOf(field.getValue()));
      } else if (field.getValue() instanceof Integer) {
        return String.valueOf(field.getValue());
      } else if (field.getValue() instanceof Float) {
        return String.valueOf(field.getValue());
      } else if (field.getValue() instanceof Double) {
        return String.valueOf(field.getValue());
      }
    }
    return String.valueOf(ws(NULL));
  }

  // add(value1, value2)
  protected String handleFieldFunction(FieldFunction field) {
    var result = new StringBuilder();
    if (field != null && field.getAggregateFunction() != null && field.getValues() != null) {
      switch (field.getAggregateFunction()) {
        case ADD:
          result.append(ws(ADD)).append(parenthesis(handleBaseFields(field.getValues())));
          break;
        case COUNT:
          result.append(ws(COUNT)).append(parenthesis(handleBaseFields(field.getValues())));
          break;
        case EXISTS:
          result.append(ws(EXISTS)).append(parenthesis(handleBaseFields(field.getValues())));
          break;
      }
    }
    return result.toString();
  }

  protected String handleFieldJoiner(FieldJoiner joiner) {
    var result = new StringBuilder();
    if (joiner != null && joiner.getFields() != null) {
      result.append(handleBaseFields(joiner.getFields(), null));
      if (joiner.isParentheses()) {
        result = new StringBuilder(parenthesis(result.toString()));
      }
    }
    return result.toString();
  }

  protected String handleTableJoiner(TableJoiner joiner) {
    var result = new StringBuilder();
    if (joiner != null && joiner.getJoins() != null) {
      for (var x = 0; x < joiner.getJoins().length; x++) {
        var join = joiner.getJoins()[x];
        result.append(handleBaseJoiner(join));
        if (x < joiner.getJoins().length - 1) {
          result.append(ws(SPACE));
        }
      }
    }
    return spacing(result.toString());
  }

  // this Field Value is temporary, in case of updates in a newer versions
  protected String handleTableJoiner(FieldAnonymous field) {
    return ws(QUESTION_MARK);
  }

  protected String handleBaseJoiner(BaseJoiner joiner) {
    var result = new StringBuilder();
    if (joiner instanceof OnJoiner) {
      result.append(handleOnJoiner((OnJoiner) joiner));
    }
    return result.toString();
  }

  protected String handleOnJoiner(OnJoiner joiner) {
    var result = new StringBuilder();
    if (joiner != null) {
      switch (joiner.getType()) {
        case INNER:
          result.append(ws(INNER));
          break;
        case LEFT:
          result.append(ws(LEFT));
          break;
        case RIGHT:
          result.append(ws(RIGHT));
          break;
        case FULL:
          result.append(ws(FULL));
          break;
      }
      if (!result.toString().isEmpty()) {
        result
            .append(ws(JOIN))
            .append(handleBaseField(joiner.getTable()))
            .append(ws(ON))
            .append(handleBaseField(joiner.getField()));
      }
    }
    return result.toString();
  }

  protected String handleFunctionOperator(FunctionOperator operator) {
    if (operator != null) {
      switch (operator) {
        case ADD:
          return ws(M_ADD);
        case SUB:
          return ws(M_SUB);
        case MUL:
          return ws(M_MUL);
        case DIV:
          return ws(M_DIV);
      }
    }
    return "";
  }

  protected String handleConditionalOperator(ConditionalOperator operator) {
    var result = new StringBuilder();
    if (operator != null) {
      switch (operator) {
        case AND:
          result.append(ws(AND));
          break;
        case OR:
          result.append(ws(OR));
          break;
        case NOT:
          result.append(ws(NOT));
          break;
      }
    }
    return result.toString();
  }

  // = value1
  // in (value1, value2)
  // between value1 AND value2
  protected String handleOperator(ComparisonOperator operator, BaseField[] values) {
    var result = new StringBuilder();
    if (operator != null && values != null && values.length > 0) {
      switch (operator) {
        case EQUAL:
          result.append(ws(EQUAL)).append(spacing(handleBaseField(values[0])));
          break;
        case NOT_EQUAL:
          result.append(ws(NOT_EQUAL)).append(spacing(handleBaseField(values[0])));
          break;
        case GREATER:
          result.append(ws(GREATER)).append(spacing(handleBaseField(values[0])));
          break;
        case GREATER_OR_EQUAL:
          result.append(ws(GREATER_OR_EQUAL)).append(spacing(handleBaseField(values[0])));
          break;
        case LESS:
          result.append(ws(LESS)).append(spacing(handleBaseField(values[0])));
          break;
        case LESS_OR_EQUAL:
          result.append(ws(LESS_OR_EQUAL)).append(spacing(handleBaseField(values[0])));
          break;
        case BETWEEN:
          result
              .append(ws(BETWEEN))
              .append(spacing(handleBaseField(values[0])))
              .append(ws(AND))
              .append(spacing(handleBaseField(values[1])));
          break;
        case LIKE:
          result.append(ws(LIKE)).append(spacing(parenthesis(handleBaseFields(values))));
          break;
        case IN:
          result.append(ws(IN)).append(spacing(parenthesis(handleBaseFields(values))));
          break;
      }
    }
    return result.toString();
  }

  protected String trimQuery(String query) {
    return query
        .replaceAll("\\s\\s\\s\\s\\s\\s", " ")
        .replaceAll("\\s\\s\\s\\s\\s", " ")
        .replaceAll("\\s\\s\\s\\s", " ")
        .replaceAll("\\s\\s\\s", " ")
        .replaceAll("\\s\\s", " ");
  }

  // endregion

  // TODO: Add UnknownKeywordException for returend keywords

  // region keywords
  /* - w stands for 'WORD'
   * - return keyword raw
   */
  public String w(String keyword) {
    return keywords.get(keyword);
  }

  /* return keyword between spaces */
  public String ws(String keyword) {
    return w(SPACE) + keywords.get(keyword) + w(SPACE);
  }

  // return keyword starts with space
  public String wsf(String keyword) {
    return w(SPACE) + keywords.get(keyword);
  }

  // return keyword ends with spaces
  public String wsl(String keyword) {
    return keywords.get(keyword) + w(SPACE);
  }

  // return keyword between quotes
  public String wq(String keyword) {
    return w(OPEN_QUOTE) + keywords.get(keyword) + w(CLOSE_QUOTE);
  }

  // return keyword between back quotes
  public String wbq(String keyword) {
    return w(OPEN_BACK_QUTE) + keywords.get(keyword) + w(CLOSE_BACK_QUTE);
  }

  // return keyword between parenthesis
  public String wp(String keyword) {
    return w(OPEN_PARENTHESIS) + keywords.get(keyword) + w(CLOSE_PARENTHESIS);
  }

  // == STANDARD FUNCTIONS

  public String quote(String value) {
    return w(DatabaseProperties.OPEN_QUOTE) + value + w(DatabaseProperties.CLOSE_QUOTE);
  }

  public String backQuote(String value) {
    return w(DatabaseProperties.OPEN_BACK_QUTE) + value + w(DatabaseProperties.CLOSE_BACK_QUTE);
  }

  // endregion

  // region functions
  public String parenthesis(String value) {
    return ws(DatabaseProperties.OPEN_PARENTHESIS)
        + value
        + ws(DatabaseProperties.CLOSE_PARENTHESIS);
  }

  public String spacing(String value) {
    return w(DatabaseProperties.SPACE) + value + w(DatabaseProperties.SPACE);
  }

  public String joinComma(String... values) {
    var joinedFields = new StringJoiner(wsl(DatabaseProperties.COMMA));
    Arrays.stream(values).forEach(joinedFields::add);
    return joinedFields.toString();
  }
  // endregion
}
