package net.jstgo.db.abstracts;

import net.jstgo.db.config.ModuleConfig;

public interface IDatabaseProperties {
  String translateQuery(DmlBuilder query);
  String createTablesQuery(ModuleConfig module);
  String alterTablesQuery(ModuleConfig module);
  String createDatabaseQuery(String databaseName);
}
