package net.jstgo.db.services;

import net.jstgo.db.config.DatabaseConfig;
import net.jstgo.db.abstracts.IDatabaseProperties;
import net.jstgo.db.types.DatabaseType;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseHealth {

  private static final Logger logger = LoggerFactory.getLogger(DatabaseHealth.class);
  private final IDatabaseProperties databaseProperties;

  public DatabaseHealth(IDatabaseProperties databaseProperties) {
    this.databaseProperties = databaseProperties;
  }

  public boolean isDatabaseAvailable(DatabaseConfig config) {
    // create database
    if (config == null || config.getType() == DatabaseType.NONE) {
      return false;
    }
    for (var i = 0; i < 10; i++) {
      try (var statement =
          DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword())
              .createStatement()) {
        var properties = config.getType().getProperties();
        var query = properties.createDatabaseQuery(config.getDatabaseName());
        statement.execute(query);
        return true;
      } catch (SQLException ignore) {
        logger.warn("Connection to Database failed, retry to connect");
        try {
          Thread.sleep(5000);
        } catch (InterruptedException exception) {
          logger.error(exception.getMessage(), exception);
        }
      }
    }
    logger.error(
        "Connection to Database failed, please correct your"
            + " 'config/settings/database.xml' file information"
            + " or check your database connection:"
            + " ---> JdbcUrl: {}\n---> Host: {}\n---> Type: {}\n---> DatabaseName: {}\n---> Username: {}",
        config.getDatasourceUrl(),
        config.getHost(),
        config.getType().name(),
        config.getDatabaseName(),
        config.getUsername());
    return false;
  }
}
