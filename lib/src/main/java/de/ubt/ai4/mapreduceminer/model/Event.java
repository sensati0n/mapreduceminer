package de.ubt.ai4.mapreduceminer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Event {

    private List<Attribute> attributes;

    public Event(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Event() {
        this.attributes = new ArrayList<>();
    }

    public void addAttribute(String key, String value) {
        this.attributes.add(new Attribute(key, value));
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }


    public Event filter(String... attributes) {
        return new Event(this.getAttributes().stream().filter(attribute -> Arrays.asList(attributes).contains(attribute.getKey())).collect(Collectors.toList()));
    }


    @Override
    public String toString()
    {
        if(attributes.size() == 0) {return ""; }

        StringBuilder sb = new StringBuilder();

        sb.append("(");

        for(Attribute attribute : attributes)
        {
            sb.append(attribute.getValue());
            sb.append(", ");
        }

        sb.delete(sb.length()-2, sb.length());
        sb.append(")");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        Event other = (Event) o;

        if(other.getAttributes().size() != this.attributes.size())
            return false;

        for (int i = 0; i < other.getAttributes().size(); i++) {
            if(other.getAttributes().get(i).getKey().equals(this.attributes.get(i).getKey()) &&
                    other.getAttributes().get(i).getValue().equals(this.attributes.get(i).getValue()))
                continue;

            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
