package net.jstgo.db.types;

import net.jstgo.db.abstracts.DatabaseProperties;
import net.jstgo.db.translators.MariadbProperties;
import net.jstgo.db.translators.MysqlProperties;
import net.jstgo.db.translators.SqlProperties;

public enum DatabaseType {
  MSSQL("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://", new SqlProperties()),
  MARIADB("org.mariadb.jdbc.Driver", "jdbc:mariadb://", new MariadbProperties()),
  MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://", new MysqlProperties()),
  // TODO: POSTGRES("org.postgresql.Driver","jdbc:postgresql://", new Postgresql()),
  NONE("org.h2.Driver", "jdbc:h2:mem:jstgo", null);

  private final String drive;
  private final String connectionUrl;
  private final DatabaseProperties properties;

  DatabaseType(String drive, String connectionUrl, DatabaseProperties properties) {
    this.drive = drive;
    this.connectionUrl = connectionUrl;
    this.properties = properties;
  }

  public String getDrive() {
    return drive;
  }

  public String getConnectionUrl() {
    return connectionUrl;
  }

  public DatabaseProperties getProperties() {
    return properties;
  }

  public static DatabaseType getType(String type) {
    switch (type) {
      case "MSSQL", "mssql":
        return DatabaseType.MSSQL;
      case "MARIADB", "mariadb":
        return DatabaseType.MARIADB;
      case "MYSQL", "mysql":
        return DatabaseType.MYSQL;
      default:
        return DatabaseType.NONE;
    }
  }

  @Override
  public String toString() {
    return super.name().toUpperCase();
  }
}
