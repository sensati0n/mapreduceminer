package mapreduceminer.model;

import javax.xml.bind.annotation.XmlAttribute;


public class Attribute {

    @XmlAttribute
    private String key;
    public String getKey() { return this.key; }
    public void setKey(String key) { this.key = key; }
    
    @XmlAttribute
    private String value;
    public String getValue() { return this.value; }
    public void setValue(String value) { this.value = value; }

    public Attribute() { }
}