package de.ubt.ai4.mapreduceminer.util;

import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.Tracebased;
import de.ubt.ai4.mapreduceminer.constraint.relation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {

    private final List<Class> allConstraints;
    private final List<ConstraintType> allConstraintTypes;

    private String eventIdentifier;
    private String additionalAttribute;

    private List<Class> constraints = new ArrayList<>();
    private List<ConstraintType> constraintTypes = new ArrayList<>();

    public Configuration() {
        this.allConstraints = new ArrayList<>();
     /*   this.allConstraints.addAll(Arrays.asList(
                Constraint.RESPONSE,
                Constraint.ALTERNATE_RESPONSE,
                Constraint.CHAIN_RESPONSE,
                Constraint.PRECEDENCE,
                Constraint.ALTERNATE_PRECEDENCE,
                Constraint.CHAIN_PRECEDENCE));
*/
        this.allConstraints.addAll(Arrays.asList(
                Response.class,
                AlternateResponse.class,
                ChainResponse.class,
                Precedence.class,
                AlternatePrecedence.class,
                ChainPrecedence.class));

        this.allConstraintTypes = new ArrayList<>();
        this.allConstraintTypes.addAll(Arrays.asList(
                ConstraintType.ACTIVATION,
                ConstraintType.TARGET,
                ConstraintType.CORRELATION
        ));

    }

    public List<Class> getConstraints() {
        return constraints;
    }


    public void setConstraints(List<Class> constraints) {
        this.constraints = constraints;
    }

    public String getEventIdentifier() {
        return this.eventIdentifier;
    }

    public Configuration setEventIdentifier(String s) {
        this.eventIdentifier = s;
        return this;
    }

    public String getAdditionalAttribute() {
        return this.additionalAttribute;
    }

    public Configuration setAdditionalAttribute(String additionalAttribute) {
        this.additionalAttribute = additionalAttribute;
        return this;
    }

    public Configuration allConstraints() {
        this.constraints = allConstraints;
        return this;
    }

    public Configuration addConstraint(Class constraint) {
        this.constraints.add(constraint);
        return this;
    }

    public Configuration addConstraints(Class... constraints) {
        Arrays.stream(constraints).forEach(constraint -> addConstraint((constraint)));
        return this;
    }


    public Configuration allConstraintTypes() {
        this.constraintTypes = allConstraintTypes;
        return this;
    }

}
