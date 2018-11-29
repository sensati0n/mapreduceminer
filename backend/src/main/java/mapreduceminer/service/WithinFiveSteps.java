package mapreduceminer.service;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.constraint.FutureConstraining;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import mapreduceminer.service.CustomAuxiliaryDatabase;

public class WithinFiveSteps extends DoubleEventConstraint implements Eventbased, FutureConstraining {

    public WithinFiveSteps(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public WithinFiveSteps() {
        
    }


    @Override
	public boolean logic(AuxiliaryDatabase ad) {

        CustomAuxiliaryDatabase cad = (CustomAuxiliaryDatabase) ad;

        if(cad.currentJ < cad.currentI+1)
            return false;

        if(cad.currentJ > cad.currentI+5)
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

        if (!cad.tasksWithinFiveSteps.contains(filteredEventB)) {
            cad.tasksWithinFiveSteps.add(filteredEventB);
            System.out.println("true");
            return true;
        } else
            return false;
        }


    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        double eta = db.getEta().get(getEventA());
        double support = sigma / eta;

        int currentEpsilon = db.getEpsilon().get(getEventA());
        double confidence = support * (currentEpsilon / (double) logSize);

        //System.out.println("Support(" + constraint.getName() + currentEntry.getKey() + ") = \t\t" + support);
        //System.out.println("Confidence(" + currentEntry.getKey() + ") = \t" + confidence);
        return new ResultElement(this.getClass().toString(), getEventA(), getEventB(), support, confidence, this.getType());
    }

   

    


}