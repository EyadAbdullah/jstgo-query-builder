package net.jstgo.db.config;

import javax.xml.bind.annotation.XmlTransient;

public class BaseConfig {

  @XmlTransient
  private String name;

  public BaseConfig() {
  }

  public BaseConfig(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
