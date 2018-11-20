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
import de.ubt.ai4.mapreduceminer.constraint.relation.Precedence;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.Attribute;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.util.Tuple;

public class NotSuccession extends DoubleEventConstraint implements Eventbased {

    public NotSuccession(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public NotSuccession() {}

    @Override
    public boolean logic(AuxilaryDatabase ad) {
       
        //RESPONSE:

        if(ad.currentJ < ad.currentI+1)
        return false;

            Event filteredEventB = super.getEventB();
            switch (super.getType()) {
                case ACTIVATION:
                    filteredEventB  = filteredEventB.filter(super.getEventIdentifier());
                    break;
                case TARGET:
                    filteredEventB = filteredEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                    break;
                case CORRELATION:
            }

            if (!ad.tasksNotSuccession.contains(filteredEventB)) {
                ad.tasksNotSuccession.add(filteredEventB);
                return true;
            } else
                return false;


    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        System.out.println("Constraint: " + this.getEventA() + ", " + this.getEventB());
        System.out.println("sigmaInit: " + sigma);

        Event precedenceEventA = new Event();
        Event precedenceEventB = new Event();


        switch(this.getType()) {
            case ACTIVATION:

            precedenceEventA.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
            
            precedenceEventB.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
            precedenceEventB.addAttribute(db.getConfiguration().getAdditionalAttribute(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get().getValue());
        break;

        case TARGET:
            precedenceEventA.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
            precedenceEventA.addAttribute(db.getConfiguration().getAdditionalAttribute(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get().getValue());
            
            precedenceEventB.addAttribute(db.getConfiguration().getEventIdentifier(), this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
   
        break;
        }

        
        //ACTIVATION (succ(ax, b))
        /**
         * re(ax, b) + pr(a, bx)
         * 
         * 
         */
        Precedence p = new Precedence();
        p.setEventA(precedenceEventA);
        p.setEventB(precedenceEventB);
        p.setType(ConstraintType.TARGET);
    
        int sigmaPrecedence = 0;
        try {
            sigmaPrecedence+=db.getSigmaEntry(Precedence.class).get(p);
            System.out.println("sigmaPrecedence: " + db.getSigmaEntry(Precedence.class).get(p));

        }
        catch(NullPointerException npe) {
            System.out.println("RTFM!");
        }

        sigma += sigmaPrecedence;

        double etaA = db.getEta().get(getEventA());
        double etaB = 0;
        try {
            etaB = db.getEta().get(precedenceEventB);

        } catch(NullPointerException npe) {}

        System.out.println("etaA: " + etaA);
        System.out.println("etaB: " + etaB);
        double supportNew = sigma/(etaA + etaB);
        double support = 1-supportNew;
        System.out.println("sigma: " + sigma);
        
        System.out.println("supportNew: " + sigma/(etaA + etaB));
        System.out.println("support: " + support);

        double confidence = 0;

        int currentEpsilon = 0;
       
    
        Tuple<Event> searchTuple = new Tuple<Event>(this.getEventA(), precedenceEventB);

    
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
       
        NotSuccession other = (NotSuccession) o;

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


