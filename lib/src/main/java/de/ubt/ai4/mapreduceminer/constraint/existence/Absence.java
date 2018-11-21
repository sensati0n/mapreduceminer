package de.ubt.ai4.mapreduceminer.constraint.existence;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.IntEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Tracebased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

import java.util.ArrayList;
import java.util.List;

public class Absence extends IntEventConstraint implements Tracebased {

    public Absence() {

    }

    public Absence(Event event, Integer n, ConstraintType type) {
        super(event, n, type);
    }

    @Override
    public boolean logic(AuxiliaryDatabase ad, int position, int size) {
 
     return true;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        return null;
    }
}
