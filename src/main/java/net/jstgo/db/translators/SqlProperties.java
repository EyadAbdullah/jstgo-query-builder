package net.jstgo.db.translators;

import net.jstgo.db.abstracts.DatabaseProperties;
import net.jstgo.db.abstracts.DmlBuilder;
import net.jstgo.db.config.ModuleConfig;
import net.jstgo.db.query.Delete;
import net.jstgo.db.query.Insert;
import net.jstgo.db.query.Select;
import net.jstgo.db.query.Update;
import java.util.Map;

public class SqlProperties extends DatabaseProperties {

  public SqlProperties() {
    super(
        Map.ofEntries(
            Map.entry(TOP, "TOP"),
            Map.entry(OPEN_BACK_QUTE, "["),
            Map.entry(CLOSE_BACK_QUTE, "]")));
  }

  // region IDatabaseProperties

  @Override
  public String translateQuery(DmlBuilder query) {
    if (query instanceof Select) {
      return trimQuery(handleSelect((Select) query));
    } else if (query instanceof Update) {
      return trimQuery(handleUpdate((Update) query));
    } else if (query instanceof Insert) {
      return trimQuery(handleInsert((Insert) query));
    } else if (query instanceof Delete) {
      return trimQuery(handleDelete((Delete) query));
    }
    return "";
  }

  @Override
  public String createDatabaseQuery(String databaseName) {
    return ws(CREATE_DB) + databaseName;
  }

  @Override
  public String createTablesQuery(ModuleConfig module) {
    var result = new StringBuilder();
    if (module != null) {
      result.append(handleDefaultTableQuery(module));
    }
    return result.toString();
  }

  @Override
  public String alterTablesQuery(ModuleConfig module) {
    return "";
  }

  // endregion

  @Override
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

  @Override
  protected String handleLimit(int limit) {
    return ws(TOP) + spacing(String.valueOf(limit));
  }
}
