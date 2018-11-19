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

public class End extends SingleEventConstraint implements Tracebased {

    public End() {

    }

    public End(Event event, ConstraintType type){
        super(event, type);
    }

    @Override
    public List<Tracebased> logic(AuxilaryDatabase ad, int position, int size) {

        List<Tracebased> result = new ArrayList<>();
        if(position == size-1)
            result.add(new End(ad.last, ConstraintType.ACTIVATION));

        return result;
    }


    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        return new ResultElement(this.getClass().toString(), getEvent(), sigma/logSize, 0.0d);
    }
}
