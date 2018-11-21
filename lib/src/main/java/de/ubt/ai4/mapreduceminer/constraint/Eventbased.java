package de.ubt.ai4.mapreduceminer.constraint;

import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;

public interface Eventbased extends Constraint {

    public abstract boolean logic(AuxiliaryDatabase ad);
}
