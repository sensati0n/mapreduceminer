package de.ai4.mapreduceminer.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Attribute {

    private String key;
    private String value;

    public Attribute(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public Attribute() {}

    @XmlAttribute
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    @XmlAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
