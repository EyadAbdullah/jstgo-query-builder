package net.jstgo.db.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "foreign")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForeignConfig {

  private String fieldReference;
  private String tableReference;
  // by default removing values without deleting its relation
  private boolean supportCascade = true; // ON UPDATE CASCADE ON DELETE CASCADE

  public String getFieldReference() {
    return fieldReference;
  }

  public void setFieldReference(String fieldReference) {
    this.fieldReference = fieldReference;
  }

  public String getTableReference() {
    return tableReference;
  }

  public void setTableReference(String tableReference) {
    this.tableReference = tableReference;
  }

  public boolean isSupportCascade() {
    return supportCascade;
  }

  public void setSupportCascade(boolean supportCascade) {
    this.supportCascade = supportCascade;
  }
}
