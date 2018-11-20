package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

public class ChainResponse extends DoubleEventConstraint implements Eventbased {

    public ChainResponse(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public ChainResponse() {}

    @Override
    public boolean logic(AuxilaryDatabase ad) {
       
        if(ad.currentJ == ad.currentI+1) {
            return true;
        }
        return false;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {

        System.out.println("CR: " + this.getEventA() + ","+this.getEventB()+ ":" + sigma);

        double eta = db.getEta().get(getEventA());
        double support = sigma / eta;

        int currentEpsilon = db.getEpsilon().get(getEventA());
        double confidence = support * (currentEpsilon / (double) logSize);
        
        return new ResultElement(this.getClass().toString(), getEventA(), getEventB(), support, confidence, this.getType());
    }

    @Override
    public int hashCode() {
        return 3^this.getEventA().hashCode() * 5^this.getEventB().hashCode() * 7^this.getType().hashCode() ;
    }

    @Override
    public boolean equals(Object o) {
       
        ChainResponse other = (ChainResponse) o;

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
