package de.ubt.ai4.mapreduceminer.util;

import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.Tracebased;
import de.ubt.ai4.mapreduceminer.constraint.existence.AtMostOne;
import de.ubt.ai4.mapreduceminer.constraint.existence.End;
import de.ubt.ai4.mapreduceminer.constraint.existence.Existence;
import de.ubt.ai4.mapreduceminer.constraint.existence.Init;
import de.ubt.ai4.mapreduceminer.constraint.existence.Participation;
import de.ubt.ai4.mapreduceminer.constraint.mutualrelation.AlternateSuccession;
import de.ubt.ai4.mapreduceminer.constraint.mutualrelation.ChainSuccession;
import de.ubt.ai4.mapreduceminer.constraint.mutualrelation.CoExistence;
import de.ubt.ai4.mapreduceminer.constraint.mutualrelation.Succession;
import de.ubt.ai4.mapreduceminer.constraint.negativerelation.NotChainSuccession;
import de.ubt.ai4.mapreduceminer.constraint.negativerelation.NotCoExistence;
import de.ubt.ai4.mapreduceminer.constraint.negativerelation.NotSuccession;
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
        this.allConstraints.addAll(Arrays.asList(
            //existence
                //Absence.class,
                AtMostOne.class,
                End.class,
               // Existence.class,
                Init.class,
                Participation.class,
            //mutualrelation
                AlternateSuccession.class,
                ChainSuccession.class,
                CoExistence.class,
                Succession.class,
            //negativerelation
                NotChainSuccession.class,
                NotCoExistence.class,
                NotSuccession.class,
            //relation
                AlternatePrecedence.class,
                AlternateResponse.class,
                ChainPrecedence.class,
                ChainResponse.class,
                Precedence.class,
                RespondedExistence.class,
                Response.class));

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
        System.out.println(constraint.getSimpleName());
        if(constraint.getSimpleName().equals("Succession"))
        {
            this.constraints.add(Response.class);
            this.constraints.add(Precedence.class);
        }
        if(constraint.getSimpleName().equals("AlternateSuccession"))
        {
            this.constraints.add(AlternateResponse.class);
            this.constraints.add(AlternatePrecedence.class);
        }
        if(constraint.getSimpleName().equals("ChainSuccession"))
        {
            this.constraints.add(ChainResponse.class);
            this.constraints.add(ChainPrecedence.class);
        }
        if(constraint.getSimpleName().equals("NotSuccession"))
        {
            this.constraints.add(Response.class);
            this.constraints.add(Precedence.class);
        }
        if(constraint.getSimpleName().equals("NotChainSuccession"))
        {
            this.constraints.add(ChainResponse.class);
            this.constraints.add(ChainPrecedence.class);
        }
       
     
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

    public Configuration activationConstraints() {
        this.constraintTypes.add(ConstraintType.ACTIVATION);
        return this;

    }

    public Configuration targetConstraints() {
        this.constraintTypes.add(ConstraintType.TARGET);
        return this;
    }

}
