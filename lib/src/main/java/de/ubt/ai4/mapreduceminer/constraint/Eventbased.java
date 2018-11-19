package de.ubt.ai4.mapreduceminer.constraint;

import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;

public interface Eventbased extends Constraint {

    public abstract boolean logic(AuxilaryDatabase ad);
}
