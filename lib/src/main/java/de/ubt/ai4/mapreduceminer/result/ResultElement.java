package de.ubt.ai4.mapreduceminer.result;

import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;


public class ResultElement {

    private String template;
    public String getTemplate() { return this.template; }
    public void setTemplate(String template) { this.template = template; }

    private Event eventA;
    public Event getEventA() { return eventA; }
    public void setEventA(Event eventA) { this.eventA = eventA; }

    private Event eventB;
    public Event getEventB() { return eventB; }
    public void setEventB(Event eventB) { this.eventB = eventB; }


    private Integer n;
    public Integer getN() { return this.n; }
    public void setN(Integer n) { this.n = n; }


    private double support;
    public double getSupport() { return support; }
    public void setSupport(double support) { this.support = support; }

    private double confidence;
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    private ConstraintType type;
    public ConstraintType getType() { return this.type; }
    public void setType(ConstraintType type) { this.type = type; }

    public ResultElement(String template, Event eventA, Event eventB, double support, double confidence, ConstraintType type) {

        this.template = template;

        this.eventA = eventA;
        this.eventB = eventB;

        this.support = support;
        this.confidence = confidence;

        this.type = type;
    }

    public ResultElement(String template, Event event, double support, double confidence, ConstraintType type) {

        this.template = template;

        this.eventA = event;

        this.support = support;
        this.confidence = confidence;

        this.type = type;

    }

    public ResultElement(String template, Event event, Integer n, double support, double confidence, ConstraintType type) {

        this.template = template;

        this.eventA = event;
        this.n = n;

        this.support = support;
        this.confidence = confidence;

        this.type = type;

    }

}