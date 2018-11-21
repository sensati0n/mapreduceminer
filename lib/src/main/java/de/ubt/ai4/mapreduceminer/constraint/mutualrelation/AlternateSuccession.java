package de.ubt.ai4.mapreduceminer.constraint.mutualrelation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.Constraint;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.constraint.relation.AlternatePrecedence;
import de.ubt.ai4.mapreduceminer.constraint.relation.Precedence;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.Attribute;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.util.Tuple;

public class AlternateSuccession extends DoubleEventConstraint implements Eventbased {

    public AlternateSuccession(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public AlternateSuccession() {}

    @Override
    public boolean logic(AuxiliaryDatabase ad) {
       
        //ALTERNATE RESPONSE
        if(ad.currentJ < ad.currentI+1)
        return false;

        Event filteredSPEventB = super.getEventB();
        Event filteredMPEventB = super.getEventB();
        Event filteredEventA = super.getEventA();

        switch(super.getType())
        {
            case ACTIVATION:
                filteredSPEventB = filteredSPEventB.filter(super.getEventIdentifier());
                filteredMPEventB = filteredMPEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                filteredEventA = filteredEventA.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                break;
            case TARGET:
                filteredSPEventB = filteredSPEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                filteredMPEventB = filteredMPEventB.filter(super.getEventIdentifier());
                filteredEventA = filteredEventA.filter(super.getEventIdentifier());
                break;
            case CORRELATION:
        }

        if (!ad.breakAlternateSuccession) {
            if(filteredEventA.equals(filteredMPEventB)) {
                ad.breakAlternateSuccession = true;
            }
            if(!ad.tasksAlternateSuccession.contains(filteredSPEventB)) {
                ad.tasksAlternateSuccession.add(filteredSPEventB);
                return true;
            }

            return false;

        }
        else
            return false;

    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        System.out.println("Constraint: " + this.getEventA() + ", " + this.getEventB());
        System.out.println("sigmaInit: " + sigma);

        Event alternatePrecedenceEventA = new Event();
        Event alternatePrecedenceEventB = new Event();


        switch(this.getType()) {
            case ACTIVATION:

            alternatePrecedenceEventA.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
            
            alternatePrecedenceEventB.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
            alternatePrecedenceEventB.addAttribute(db.getConfiguration().getAdditionalAttribute(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get().getValue());
        break;

        case TARGET:
        alternatePrecedenceEventA.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
        alternatePrecedenceEventA.addAttribute(db.getConfiguration().getAdditionalAttribute(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get().getValue());
            
        alternatePrecedenceEventB.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
   
        break;
        }

        
        //ACTIVATION (succ(ax, b))
        /**
         * re(ax, b) + pr(a, bx)
         * 
         * 
         */
        AlternatePrecedence p = new AlternatePrecedence();
        p.setEventA(alternatePrecedenceEventA);
        p.setEventB(alternatePrecedenceEventB);
        p.setType(ConstraintType.TARGET);
    
        int sigmaPrecedence = 0;
        try {
            sigmaPrecedence+=db.getSigmaEntry(AlternatePrecedence.class).get(p);
            System.out.println("sigmaPrecedence: " + db.getSigmaEntry(AlternatePrecedence.class).get(p));

        }
        catch(NullPointerException npe) {
            System.out.println("RTFM!");
        }

        sigma += sigmaPrecedence;

        double etaA = db.getEta().get(getEventA());
        double etaB = 0;
        try {
            etaB = db.getEta().get(alternatePrecedenceEventB);

        } catch(NullPointerException npe) {}

        System.out.println("etaA: " + etaA);
        System.out.println("etaB: " + etaB);
        double support = sigma/(etaA + etaB);

        double confidence = 0;

        int currentEpsilon = 0;
       
    
        Tuple<Event> searchTuple = new Tuple<Event>(this.getEventA(), alternatePrecedenceEventB);

    
            try {
                currentEpsilon += db.getTwoDimEpsilon().get(searchTuple);
    
            }
            catch(NullPointerException npe) {
    
            }
            confidence = support * (currentEpsilon / (double) logSize);

           
    

        //System.out.println("Support(" + constraint.getName() + currentEntry.getKey() + ") = \t\t" + support);
        //System.out.println("Confidence(" + currentEntry.getKey() + ") = \t" + confidence);
        return new ResultElement(this.getClass().toString(), getEventA(), getEventB(), support, confidence, this.getType());
    }


    @Override
    public int hashCode() {
        return 3^this.getEventA().hashCode() * 5^this.getEventB().hashCode() * 7^this.getType().hashCode() ;
    }

    @Override
    public boolean equals(Object o) {
       
        AlternateSuccession other = (AlternateSuccession) o;

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


