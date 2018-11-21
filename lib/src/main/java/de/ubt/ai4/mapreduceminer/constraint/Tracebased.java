package de.ubt.ai4.mapreduceminer.constraint;

import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;

import java.util.List;

public interface Tracebased extends Constraint {

    boolean logic(AuxiliaryDatabase ad, int position, int size);


}
