package de.ubt.ai4.mapreduceminer.constraint.existence;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.SingleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Tracebased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

import java.util.ArrayList;
import java.util.List;

public class Participation extends SingleEventConstraint implements Tracebased {

    public Participation() {

    }

    public Participation(Event event, ConstraintType type) {
        super(event, type);

    }

    @Override
    public List<Tracebased> logic(AuxilaryDatabase ad, int position, int size) {
        List<Tracebased> result = new ArrayList<>();
        result.add(new Participation(this.getEvent(), ConstraintType.ACTIVATION));
        return result;

    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        double eta = db.getEta().get(getEvent());
        double support = sigma / logSize;

        int currentEpsilon = db.getEpsilon().get(getEvent());
        double confidence = support * (currentEpsilon / (double) logSize);

        return new ResultElement(this.getClass().toString(), getEvent(),  support, confidence);
    }
}
