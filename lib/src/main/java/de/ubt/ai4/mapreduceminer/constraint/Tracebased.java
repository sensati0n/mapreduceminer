package de.ubt.ai4.mapreduceminer.constraint;

import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;

import java.util.List;

public interface Tracebased extends Constraint {

    List<Tracebased> logic(AuxilaryDatabase ad, int position, int size);


}
