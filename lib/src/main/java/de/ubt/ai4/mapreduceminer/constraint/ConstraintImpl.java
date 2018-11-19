package de.ubt.ai4.mapreduceminer.constraint;

import de.ubt.ai4.mapreduceminer.util.ConstraintType;


public abstract class ConstraintImpl implements Constraint {

    static String eventIdentifier ;
    static String additionalAttribute ;
    ConstraintType type;

    public ConstraintImpl(ConstraintType type) {
        this.type = type;
    }

    public ConstraintImpl() {

    }

    public static String getEventIdentifier() {
        return eventIdentifier;
    }

    public static void setEventIdentifier(String eventIdentifier) {
        ConstraintImpl.eventIdentifier = eventIdentifier;
    }

    public static String getAdditionalAttribute() {
        return additionalAttribute;
    }

    public static void setAdditionalAttribute(String additionalAttribute) {
        ConstraintImpl.additionalAttribute = additionalAttribute;
    }


    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }




}
