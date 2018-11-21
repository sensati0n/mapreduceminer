package mapreduceminer.service;

import java.util.ArrayList;
import java.util.List;

import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
;

public class CustomAuxiliaryDatabase extends AuxiliaryDatabase {

    public  List<Event> tasksWithinFiveSteps = new ArrayList<>();

    public CustomAuxiliaryDatabase() {
        super();
    }

    public void clearEventBased() {
        super.clearEventBased();
        tasksWithinFiveSteps.clear();

    }
}