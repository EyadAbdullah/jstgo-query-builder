package net.jstgo.db.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import net.jstgo.db.enums.FieldPriority;
import net.jstgo.db.enums.FieldType;

@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldConfig extends BaseConfig {

  @XmlElement(name = "type", required = true)
  private FieldType fieldType;

  @XmlElement(name = "default")
  private String def;

  @XmlElement(name = "options")
  private String options;

  @XmlElement(name = "priority", defaultValue = "MIDDLE")
  private FieldPriority priority = FieldPriority.MIDDLE;

  @XmlElementWrapper(name = "extraFields")
  @XmlElement(name = "field")
  private List<FieldConfig> extraFields = new ArrayList<>();

  // if the module type is list this parentModule will have the build ModuleList
  @XmlTransient
  private ModuleConfig parentModule;

  @XmlTransient
  private ModuleConfig listModule;

  @XmlElement(name = "readable")
  private boolean readable = true;

  private boolean isNative = true;
  private short length = 255; // default
  private boolean notNull;
  private boolean unique;
  private boolean secure;
  private boolean primaryKey;
  private ForeignConfig foreign;

  public FieldType getFieldType() {
    return fieldType;
  }

  public void setFieldType(FieldType fieldType) {
    this.fieldType = fieldType;
  }

  public String getDefault() {
    return def;
  }

  public void setDefault(String def) {
    this.def = def;
  }

  public String getOptions() {
    return options;
  }

  public void setOptions(String options) {
    this.options = options;
  }

  public short getLength() {
    return length;
  }

  public void setLength(short length) {
    this.length = length;
  }

  public boolean isNotNull() {
    return notNull;
  }

  public void setNotNull(boolean notNull) {
    this.notNull = notNull;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(boolean unique) {
    this.unique = unique;
  }

  public boolean isSecure() {
    return secure;
  }

  public void setSecure(boolean secure) {
    this.secure = secure;
  }

  public boolean isPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(boolean primaryKey) {
    this.primaryKey = primaryKey;
  }

  public ForeignConfig getForeign() {
    return foreign;
  }

  public void setForeign(ForeignConfig foreign) {
    this.foreign = foreign;
  }

  public FieldPriority getPriority() {
    return priority;
  }

  public void setPriority(FieldPriority priority) {
    this.priority = priority;
  }

  public List<FieldConfig> getExtraFields() {
    return extraFields;
  }

  public void setExtraFields(List<FieldConfig> extraFields) {
    this.extraFields = extraFields;
  }

  public ModuleConfig getParentModule() {
    return parentModule;
  }

  public void setParentModule(ModuleConfig parentModule) {
    this.parentModule = parentModule;
  }

  public ModuleConfig getListModule() {
    return listModule;
  }

  public void setListModule(ModuleConfig listModule) {
    this.listModule = listModule;
  }

  public boolean isReadable() {
    return readable;
  }

  public void setReadable(boolean readable) {
    this.readable = readable;
  }

  public boolean isNative() {
    if (this.fieldType == FieldType.LIST) {
      return false;
    } else {
      return isNative;
    }
  }

  public void setNative(boolean aNative) {
    isNative = aNative;
  }

}
