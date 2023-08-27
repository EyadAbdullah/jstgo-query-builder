package net.jstgo.db.config;

import net.jstgo.db.abstracts.DmlBuilder;
import net.jstgo.db.types.DatabaseType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "database")
@XmlType(
    propOrder = {
      "host",
      "username",
      "password",
      "databaseName",
      "type",
      "port",
      "initialSize",
      "maxWait",
      "maxActive",
      "maxIdle",
      "minIdle"
    })
@XmlAccessorType(XmlAccessType.FIELD)
public class DatabaseConfig {
  private DatabaseType type = DatabaseType.NONE;
  private String databaseName;
  private String username;
  private String password;
  private String host;
  private int port;
  private int initialSize;
  private int maxWait;
  private int maxActive;
  private int maxIdle;
  private int minIdle;

  public String getUrl() {
    if (type != DatabaseType.NONE) {
      return type.getConnectionUrl() + host + ":" + port;
    }
    return null;
  }

  public String getDatasourceUrl() {
    if (type != DatabaseType.NONE) {
      return type.getConnectionUrl() + host + ":" + port + "/" + databaseName;
    }  else {
      return type.getConnectionUrl();
    }
  }

  public String translateQuery(DmlBuilder builder) throws IllegalStateException {
    if (type != null && type != DatabaseType.NONE && type.getProperties() != null) {
      return type.getProperties().translateQuery(builder);
    }
    throw new IllegalStateException("Query translation is not Possible");
  }

  public DatabaseType getType() {
    return type;
  }

  public void setType(DatabaseType type) {
    this.type = type;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getInitialSize() {
    return initialSize;
  }

  public void setInitialSize(int initialSize) {
    this.initialSize = initialSize;
  }

  public int getMaxWait() {
    return maxWait;
  }

  public void setMaxWait(int maxWait) {
    this.maxWait = maxWait;
  }

  public int getMaxActive() {
    return maxActive;
  }

  public void setMaxActive(int maxActive) {
    this.maxActive = maxActive;
  }

  public int getMaxIdle() {
    return maxIdle;
  }

  public void setMaxIdle(int maxIdle) {
    this.maxIdle = maxIdle;
  }

  public int getMinIdle() {
    return minIdle;
  }

  public void setMinIdle(int minIdle) {
    this.minIdle = minIdle;
  }
}
