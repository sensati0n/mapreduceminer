package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.constraint.HistoryBased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

public class Precedence extends DoubleEventConstraint implements Eventbased, HistoryBased {

    public Precedence(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public Precedence() {}

    @Override
    public boolean logic(AuxilaryDatabase ad) {



        System.out.println("I am alive");
        if(ad.currentJ < ad.currentI+1)
            return false;

        Event filteredEventB = super.getEventB();
        Event filteredEventA = super.getEventA();

        switch (super.getType()) {
            case ACTIVATION: //TARGET
            filteredEventB = filteredEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
            filteredEventA = filteredEventA.filter(super.getEventIdentifier(), super.getAdditionalAttribute());

                break;
            case TARGET:
            filteredEventB = filteredEventB.filter(super.getEventIdentifier());
            filteredEventA = filteredEventA.filter(super.getEventIdentifier());

                break;
            case CORRELATION:
        }

        if (!ad.breakPrecedence) {
            if (filteredEventA.equals(filteredEventB))
                ad.breakPrecedence = true;

            return true;
        }
        return false;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        
        double eta = db.getEta().get(getEventB());
        System.out.println(this.getEventA() + "," + this.getEventB() + "\t" + sigma);
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
       
        Precedence other = (Precedence) o;

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
