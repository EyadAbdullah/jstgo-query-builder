package net.jstgo.db.config;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "index")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndexConfig extends BaseConfig {

  @XmlElement
  private List<String> fields;

  @Override
  @XmlElement
  public String getName() {
    return super.getName();
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }
}
