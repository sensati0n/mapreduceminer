package de.ubt.ai4.mapreduceminer.constraint.negativerelation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.Constraint;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.constraint.relation.ChainPrecedence;
import de.ubt.ai4.mapreduceminer.constraint.relation.Precedence;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.Attribute;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.util.Tuple;

public class NotChainSuccession extends DoubleEventConstraint implements Eventbased {

    public NotChainSuccession(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public NotChainSuccession() {}

    @Override
    public boolean logic(AuxiliaryDatabase ad) {
       
        //CHAIN RESPONSE
        if(ad.currentJ == ad.currentI+1) {
            return true;
        }
        return false;

    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
      
        Event chainPrecedenceEventA = new Event();
        Event chainPrecedenceEventB = new Event();


        switch(this.getType()) {
            case ACTIVATION:

            chainPrecedenceEventA.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
            
            chainPrecedenceEventB.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
            chainPrecedenceEventB.addAttribute(db.getConfiguration().getAdditionalAttribute(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get().getValue());
        break;

        case TARGET:
        chainPrecedenceEventA.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
        chainPrecedenceEventA.addAttribute(db.getConfiguration().getAdditionalAttribute(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get().getValue());
            
        chainPrecedenceEventB.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
   
        break;
        }

        ChainPrecedence p = new ChainPrecedence();
        p.setEventA(chainPrecedenceEventA);
        p.setEventB(chainPrecedenceEventB);
        p.setType(ConstraintType.TARGET);
    
        int sigmaPrecedence = 0;
        try {
            sigmaPrecedence+=db.getSigmaEntry(ChainPrecedence.class).get(p);

        }
        catch(NullPointerException npe) {
        }

        sigma += sigmaPrecedence;

        double etaA = db.getEta().get(getEventA());
        double etaB = 0;
        try {
            etaB = db.getEta().get(chainPrecedenceEventB);

        } catch(NullPointerException npe) {}

        double supportNew = sigma/(etaA + etaB);
        double support = 1- supportNew;

        double confidence = 0;

        int currentEpsilon = 0;
       
    
        Tuple<Event> searchTuple = new Tuple<Event>(this.getEventA(), chainPrecedenceEventB);

    
            try {
                currentEpsilon += db.getTwoDimEpsilon().get(searchTuple);
    
            }
            catch(NullPointerException npe) {
    
            }
            confidence = support * (currentEpsilon / (double) logSize);


        return new ResultElement(this.getClass().toString(), getEventA(), getEventB(), support, confidence, this.getType());
    }


    @Override
    public int hashCode() {
        return 3^this.getEventA().hashCode() * 5^this.getEventB().hashCode() * 7^this.getType().hashCode() ;
    }

    @Override
    public boolean equals(Object o) {
       
        NotChainSuccession other = (NotChainSuccession) o;

        if(other.getEventA().equals(this.getEventA())) {
            if(other.getEventB().equals(this.getEventB())) {
                if(other.getType().equals(this.getType()))
                {
                    return true;
                }
            }
        }

        return false;
    }


}


