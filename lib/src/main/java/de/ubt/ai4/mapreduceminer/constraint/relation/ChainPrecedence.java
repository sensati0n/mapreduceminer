package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.constraint.HistoryBased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

public class ChainPrecedence extends DoubleEventConstraint implements Eventbased, HistoryBased {

    public ChainPrecedence(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public ChainPrecedence() { }
    
    @Override
    public boolean logic(AuxiliaryDatabase ad) {
        if(ad.currentJ == ad.currentI+1) 
            return true;
        
            return false;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        double eta = db.getEta().get(getEventB());
        double support = sigma / eta;

        int currentEpsilon = db.getEpsilon().get(getEventB());
        double confidence = support * (currentEpsilon / (double) logSize);

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
       
        ChainPrecedence other = (ChainPrecedence) o;

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
