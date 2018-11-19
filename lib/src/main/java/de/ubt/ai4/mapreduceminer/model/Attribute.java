package de.ubt.ai4.mapreduceminer.model;

public class Attribute {

    private String key;
    private String value;

    public Attribute(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public Attribute() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
