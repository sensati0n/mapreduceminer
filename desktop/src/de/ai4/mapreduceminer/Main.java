package de.ai4.mapreduceminer;

import de.ai4.mapreduceminer.model.Event;
import de.ai4.mapreduceminer.model.EventLog;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws JAXBException {

        String pathToXes = null;
        try
        {
            pathToXes = args[0];
        }
        catch (ArrayIndexOutOfBoundsException aioobe)
        {
            System.err.println("Usage: java Main.java [xesFile]");
            System.exit(1);
        }

        //Load XES-File into a EventLog instance
        pathToXes = args[0];
        File xesFile = new File(pathToXes);

        EventLog eventLog = XesLoader.loadXes(xesFile);


        //MR-I: produce Key-Value-Pairs
        Database db = eventLog.getTraces().stream().map(
                trace ->
                {
                    Database database = new Database();

                    List<Event> eventsA = trace.getEvents();
                    List<Event> eventsB = trace.getEvents();

                    List<String> tasksResponse = new ArrayList<>();
                    List<Event> uniqueEventsInTrace = new ArrayList<>();

                    for (int i = 0; i < trace.getEvents().size(); i++) {

                        /**
                         * ETA
                         */
                        database.addEta(eventsA.get(i), 1);
                        if (("dimensionsA").equals("dimensionsB")) {
                            database.addEta(eventsB.get(i), 1);
                        }

                        //for epsilon:
                        if (!uniqueEventsInTrace.contains(eventsA.get(i))) {
                            uniqueEventsInTrace.add(eventsA.get(i));
                        }if (!uniqueEventsInTrace.contains(eventsB.get(i))) {
                            uniqueEventsInTrace.add(eventsA.get(i));
                        }

                        for (int j = i + 1; j < trace.getEvents().size(); j++) {
                            if (!tasksResponse.contains(eventsB.get(j).toString())) {
                                database.addSigma(Constraint.RESPONSE, new Tuple<>(eventsA.get(i), eventsB.get(j)), 1);
                                tasksResponse.add(eventsB.get(j).toString());
                            }
                        }
                        tasksResponse.clear();
                    }

                    /**
                     * EPSILON
                     */
                    for(Event uniqueEvent : uniqueEventsInTrace) {
                        database.addEpsilon(uniqueEvent, 1);
                    }
                        return database;
                }
        ).reduce((accDb, currentDb) -> {

                    /**
                     * ETA
                     */
                    for (Map.Entry<Event, Integer> currentEntry : currentDb.getEta().entrySet()) {
                        int currentDbValue = currentEntry.getValue();
                        accDb.addEta(currentEntry.getKey(), currentDbValue);
                    }

                    /**
                     * EPSILON
                     */
                    for (Map.Entry<Event, Integer> currentEntry : currentDb.getEpsilon().entrySet()) {
                        int currentDbValue = currentEntry.getValue();
                        accDb.addEpsilon(currentEntry.getKey(), currentDbValue);
                    }

                    /**
                     * RESPONSE
                     */
                    for (Map.Entry<Tuple<Event>, Integer> currentEntry : currentDb.getResponse().entrySet()) {
                        Tuple<Event> currentTuple = currentEntry.getKey();
                        int currentDbValue = currentEntry.getValue();
                        accDb.addSigma(Constraint.RESPONSE, currentTuple, currentDbValue);

                    }
                    return accDb;
                }
        ).get();


        //'MR-II': calculate Support and Confidence
        Map<Event, Integer> eta = db.getEta();
        Map<Event, Integer> epsilon = db.getEpsilon();
        //Response
        System.out.println("RESPONSE");
        for (Map.Entry<Tuple<Event>, Integer> currentEntry : db.getResponse().entrySet()) {

            Event currentEvent = currentEntry.getKey().x;
            int currentEta = eta.get(currentEvent);
            double support = currentEntry.getValue() / (double) currentEta;

            int currentEpsilon = epsilon.get(currentEvent);
            double confidence = support * (currentEpsilon / (double) eventLog.getTraces().size());

            System.out.println("Support(" + currentEntry.getKey() + ") = \t\t" + support);
            System.out.println("Confidence(" + currentEntry.getKey() + ") = \t" + confidence);
        }


        System.out.println(db);
    }


}
