package de.ubt.ai4.mapreduceminer.constraint.existence;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.IntEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Tracebased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

import java.util.ArrayList;
import java.util.List;

public class Existence extends IntEventConstraint implements Tracebased {

    public Existence() {
    super();
    }


    public Existence(Event event, ConstraintType type){
        super(event, type);
    }

    public Existence(Event event, Integer n, ConstraintType type){
        super(event, n, type);
    }


    @Override
    public List<Tracebased> logic(AuxilaryDatabase ad, int position, int size) {
        List<Tracebased> result = new ArrayList<>();

        for(int i = 0; i < ad.getOccurences(getEvent()); i++)
        {
            result.add(new Existence(getEvent(), i, getType()));
        }
        return result;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        double eta = db.getEta().get(getEvent());
        double support = sigma / logSize;

        int currentEpsilon = db.getEpsilon().get(getEvent());
        double confidence = support * (currentEpsilon / (double) logSize);

        return new ResultElement(this.getClass().toString(), getEvent(), getN(), support, confidence);
    }
}
