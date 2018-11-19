package de.ubt.ai4.mapreduceminer.constraint;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.result.ResultElement;

public interface Constraint {

    ResultElement getResult(Database db, double sigma, int logSize);
}
