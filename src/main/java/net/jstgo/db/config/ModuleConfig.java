package net.jstgo.db.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import net.jstgo.db.enums.ModuleType;

@XmlRootElement(name = "module")
@XmlAccessorType(XmlAccessType.FIELD)
public class ModuleConfig extends BaseConfig {

  @XmlTransient
  private List<FieldConfig> fields = new ArrayList<>();

  @XmlTransient
  private List<IndexConfig> indexes = new ArrayList<>();

  @XmlElement
  private ModuleType type = ModuleType.MODULE;

  @XmlTransient
  private ModuleConfig parentModule;

  public List<FieldConfig> getFields() {
    return fields;
  }

  public void setFields(List<FieldConfig> fields) {
    this.fields = fields;
  }

  public List<IndexConfig> getIndexes() {
    return indexes;
  }

  public ModuleType getType() {
    return type;
  }

  public void setType(ModuleType type) {
    this.type = type;
  }

  public ModuleConfig getParentModule() {
    return parentModule;
  }

  public void setParentModule(ModuleConfig parentModule) {
    this.parentModule = parentModule;
  }

}
