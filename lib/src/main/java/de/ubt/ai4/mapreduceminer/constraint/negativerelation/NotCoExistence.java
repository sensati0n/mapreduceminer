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
import de.ubt.ai4.mapreduceminer.model.Attribute;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.util.Tuple;

public class NotCoExistence extends DoubleEventConstraint implements Eventbased {

    public NotCoExistence(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public NotCoExistence() {}

    @Override
    public boolean logic(AuxilaryDatabase ad) {
        
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

        if(ad.currentI != ad.currentJ) {
            if (!ad.tasksNotCoExistence.contains(filteredEventB)) {
                ad.tasksNotCoExistence.add(filteredEventB);
                return true;
            }
        }

        return false;
    
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {

        System.out.println("Calc:" + this.getEventA() + "," + this.getEventB());
        Map.Entry<Constraint, Integer> secondSigmaConstraint = null;

        Event fantasieEvent = new Event();
        Event ev = new Event();
        double etaA = db.getEta().get(getEventA());
        double etaB = 0;
        double support = 0;
        double confidence = 0;
        int currentEpsilon = 0;
        Tuple<Event> searchTuple = new Tuple();
      
            switch(this.getType()) {
                case ACTIVATION:
                fantasieEvent.addAttribute(db.getConfiguration().getEventIdentifier() ,this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
                fantasieEvent.addAttribute(db.getConfiguration().getAdditionalAttribute() ,this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get().getValue());
                ev.addAttribute(db.getConfiguration().getEventIdentifier() , this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());

               // Attribute attributeA =  this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get(); 

                List<Map.Entry<Constraint, Integer>> list =  db.getSigmaEntry(this.getClass()).entrySet().stream().filter(constraint -> {         
                    NotCoExistence castedConstraint2 = (NotCoExistence) constraint.getKey(); //(bx, a)

                    if(castedConstraint2.getEventA().equals(fantasieEvent) && castedConstraint2.getEventB().filter(db.getConfiguration().getEventIdentifier()).equals(ev))
                        return true; 
                    return false;                           
                    }).collect(Collectors.toList());
                   
                    if(list.size()>0) {
                        System.out.println("List>0!!!");
                        secondSigmaConstraint = list.get(0);
                    }


           
                    System.out.println("fEvent" + fantasieEvent);
                    try {
                        etaB = db.getEta().get(fantasieEvent);
                        System.out.println("etaB:" + etaB);
                    }
                   catch(NullPointerException npw) {
                    System.out.println("'ETA EXCEPTION");
                    
                   }
        
                try{
                    sigma += secondSigmaConstraint.getValue();
                   
        
                }
                catch(NullPointerException npe) {
                    //No partner found
                    System.out.println("EXCEPTION");
        
                }
        
        
                System.out.println("etaA:" + etaA);
        
                System.out.println("SigmaNeu:" + sigma);
        
                support = sigma / (etaA+etaB);

                
                searchTuple = new Tuple<Event>(this.getEventA(), fantasieEvent);

        
                try {
                    currentEpsilon += db.getTwoDimEpsilon().get(searchTuple);
        
                }
                catch(NullPointerException npe) {
                    System.out.println("NPEE!!!");
        
                }
                System.out.println("currentEps:" + currentEpsilon);

                confidence = support * (currentEpsilon / (double) logSize);

                break;


                case TARGET: //(a, by) (b, ay)
                fantasieEvent.addAttribute(db.getConfiguration().getEventIdentifier() ,this.getEventA().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
                fantasieEvent.addAttribute(db.getConfiguration().getAdditionalAttribute() ,this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get().getValue());
                ev.addAttribute(db.getConfiguration().getEventIdentifier() , this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getEventIdentifier())).findFirst().get().getValue());
                
                //Attribute attributeB =  this.getEventB().getAttributes().stream().filter(attr -> attr.getKey().equals(db.getConfiguration().getAdditionalAttribute())).findFirst().get(); 

                    List<Map.Entry<Constraint, Integer>> listTarget =  db.getSigmaEntry(this.getClass()).entrySet().stream().filter(constraint -> {
                        NotCoExistence castedConstraint2 = (NotCoExistence) constraint.getKey(); //(b, ay)

                       if(castedConstraint2.getEventB().equals(fantasieEvent) && castedConstraint2.getEventA().filter(db.getConfiguration().getEventIdentifier()).equals(ev) ) {
                                    return true;
                                }
                return false;
                
                }).collect(Collectors.toList());
                if(listTarget.size()>0) {
                    System.out.println("List>0!!!");

                    secondSigmaConstraint = listTarget.get(0);
                }
                
                
           
                System.out.println("fEvent" + ev);
                try {
                    etaB = db.getEta().get(ev);
                    System.out.println("etaB:" + etaB);
                }
               catch(NullPointerException npw) {
                System.out.println("'ETA EXCEPTION");
                
               }
    
            try{
                sigma += secondSigmaConstraint.getValue();
               
    
            }
            catch(NullPointerException npe) {
                //No partner found
                System.out.println("EXCEPTION");
    
            }
    
    
            System.out.println("etaA:" + etaA);
    
            System.out.println("SigmaNeu:" + sigma);
    
            support = sigma / (etaA+etaB);


            searchTuple = new Tuple<Event>(this.getEventA(), ev);

    
            try {
                currentEpsilon += db.getTwoDimEpsilon().get(searchTuple);
    
            }
            catch(NullPointerException npe) {
    
            }
            confidence = support * (currentEpsilon / (double) logSize);

                break;
            }

           
    

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
       
        NotCoExistence other = (NotCoExistence) o;

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


