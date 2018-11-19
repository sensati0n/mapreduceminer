package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.JobRunner;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;

import javax.xml.crypto.Data;

public class Response extends DoubleEventConstraint implements Eventbased {

    public Response(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public Response() {

    }


    @Override
    public boolean logic(AuxilaryDatabase ad) {
        Event filteredEventB = super.getEventB();
        switch (super.getType()) {
            case ACTIVATION:
                filteredEventB = filteredEventB.filter(super.getEventIdentifier());
                break;
            case TARGET:
                filteredEventB = filteredEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                break;
            case CORRELATION:
        }

        if (!ad.tasksResponse.contains(filteredEventB)) {
            ad.tasksResponse.add(filteredEventB);
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
        return new ResultElement(this.getClass().toString(), getEventA(), getEventB(), support, confidence);

    }
}
