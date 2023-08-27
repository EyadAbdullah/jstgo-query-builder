package net.jstgo.db.services;

import net.jstgo.db.config.DatabaseConfig;
import net.jstgo.db.query.Delete;
import net.jstgo.db.query.Insert;
import net.jstgo.db.query.Update;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionManager {

  private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);
  private final DataSource dataSource;
  private final DatabaseConfig databaseConfig;
  private Connection current;
  private Map<String, Savepoint> savePoints;

  public TransactionManager(DataSource dataSource, DatabaseConfig databaseConfig) {
    this.dataSource = dataSource;
    this.databaseConfig = databaseConfig;
  }

  public TransactionManager beginTransaction() throws SQLException {
    current = dataSource.getConnection();
    current.setAutoCommit(false);
    savePoints = new HashMap<>();
    return this;
  }

  public TransactionManager joinTransaction(Void callbackTransaction) throws Exception {
    if (callbackTransaction != null) {
      callbackTransaction.execute(current);
    }
    return this;
  }

  public TransactionManager setSavePoint(String name) throws SQLException {
    savePoints.put(name, current.setSavepoint());
    return this;
  }

  public TransactionManager rollback() throws SQLException {
    current.rollback();
    return this;
  }

  public TransactionManager rollback(String savePoint) throws SQLException {
    current.rollback(savePoints.get(savePoint));
    return this;
  }

  public void commit() throws SQLException {
    current.commit();
  }

  public void insertSingle(Insert insertQuery, List<Object> values) {
    var query = databaseConfig.translateQuery(insertQuery);
    if (StringUtils.isBlank(query)) {
      logger.debug("invalid insert query");
      return;
    }
    logger.info(query);
    try (var connection = dataSource.getConnection()) {
      try (var prepared = connection.prepareStatement(query)) {
        // set values
        for (var x = 0; x < values.size(); x++) {
          prepared.setObject(x + 1, values.get(x));
        }
        prepared.executeUpdate();
      }
    } catch (SQLIntegrityConstraintViolationException duplicates) {
      // ignore duplicates
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
    }
  }

  public void insert(Insert insertQuery, List<List<Object>> objects) {
    var query = databaseConfig.translateQuery(insertQuery);
    if (StringUtils.isBlank(query) ||
        ArrayUtils.isEmpty(insertQuery.getFields()) ||
        ArrayUtils.isEmpty(insertQuery.getValues().toArray())) {
      logger.debug("invalid insert query");
      return;
    }
    logger.info(query);
    try (var connection = dataSource.getConnection()) {
      try (var prepared = connection.prepareStatement(query)) {
        // set values
        for (var obj : objects) {
          for (var x = 0; x < obj.size(); x++) {
            prepared.setObject(x + 1, obj.get(x));
          }
          prepared.addBatch();
        }
        prepared.executeBatch();
      }
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
    }
  }

  public void updateSingle(Update insertQuery, List<Object> values) {
    var query = databaseConfig.translateQuery(insertQuery);
    if (StringUtils.isBlank(query) ||
        ArrayUtils.isEmpty(insertQuery.getFields())) {
      logger.debug("invalid update query");
      return;
    }
    logger.info(query);
    try (var connection = dataSource.getConnection()) {
      try (var prepared = connection.prepareStatement(query)) {
        // set values
        for (var x = 0; x < values.size(); x++) {
          prepared.setObject(x + 1, values.get(x));
        }
        prepared.executeUpdate();
      }
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
    }
  }

  public void update(Update update, List<List<Object>> objects) {
    var query = databaseConfig.translateQuery(update);
    if (StringUtils.isBlank(query)) {
      logger.debug("invalid update query");
      return;
    }
    logger.info(query);
    try (var connection = dataSource.getConnection()) {
      try (var prepared = connection.prepareStatement(query)) {
        // set values
        for (var obj : objects) {
          for (var x = 0; x < obj.size(); x++) {
            prepared.setObject(x + 1, obj.get(x));
          }
          prepared.addBatch();
        }
        prepared.executeBatch();
      }
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
    }
  }

  public void deleteSingle(Delete deleteQuery, List<Object> values) {
    var query = databaseConfig.translateQuery(deleteQuery);
    if (StringUtils.isBlank(query)) {
      logger.debug("invalid delete query");
      return;
    }
    logger.info(query);
    try (var connection = dataSource.getConnection()) {
      try (var prepared = connection.prepareStatement(query)) {
        // set values
        for (var x = 0; x < values.size(); x++) {
          prepared.setObject(x + 1, values.get(x));
        }
        prepared.executeUpdate();
      }
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
    }
  }

  public <T> T localTransaction(@NotNull Callback<T> callbackTransaction) {
    T result = null;
    try (var connection = dataSource.getConnection()) {
      result = callbackTransaction.execute(connection);
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
    }
    return result;
  }

  public void voidTransaction(@NotNull Void callbackTransaction) {
    try (var connection = dataSource.getConnection()) {
      callbackTransaction.execute(connection);
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
    }
  }

  public <T> T executeConfig(DatabaseConfig config, @NotNull Callback<T> callbackTransaction)
      throws Exception {
    try (Connection connection =
        DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword())) {
      callbackTransaction.execute(connection);
    } catch (SQLException e) {
      logger.warn("Connection to Database failed, retry to connect");
    }
    return null;
  }

  public interface Callback<T> {

    T execute(Connection conn) throws Exception;
  }

  public interface Void {

    void execute(Connection conn) throws Exception;
  }

}
