package de.ubt.ai4.mapreduceminer.constraint.existence;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.SingleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Tracebased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Init extends SingleEventConstraint implements Tracebased {

    public Init() {

    }

    public Init(Event event, ConstraintType type) {
        super(event, type);

    }

    @Override
    public boolean logic(AuxiliaryDatabase ad, int position, int size) {

        if(position == 0)
            return true;
            else
            return false;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        return new ResultElement(this.getClass().toString(), getEvent(), sigma/logSize, 0.0d, this.getType());
    }

    @Override
    public int hashCode() {
        return 3^this.getEvent().hashCode() * 5^this.getType().hashCode() ;
    }

    @Override
    public boolean equals(Object o) {
       
        Init other = (Init) o;

        if(other.getEvent().equals(this.getEvent())) {
            if(other.getType().equals(this.getType()))
            {
                return true;
            }
        }

        return false;
    }
}
