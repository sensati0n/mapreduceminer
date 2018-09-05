package de.ai4.mapreduceminer;

import de.ai4.mapreduceminer.model.Attribute;
import de.ai4.mapreduceminer.model.Event;
import de.ai4.mapreduceminer.model.EventLog;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static List<String> dimensionsA;
    static List<String> dimensionsB;


    public static void main(String[] args) throws JAXBException {

        String pathToXes = null;
        try {
            pathToXes = args[0];
            dimensionsA = Arrays.asList(args[1].split(","));
            dimensionsB = Arrays.asList(args[2].split(","));
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.err.println("Usage: java Main.java xesFile dimensionsA dimensionsB ");
            System.exit(1);
        }

        //Load XES-File into a EventLog instance
        pathToXes = args[0];
        File xesFile = new File(pathToXes);

        EventLog eventLog = XesLoader.loadXes(xesFile);

        List<Constraint> consideredConstraints = Arrays.asList(Constraint.RESPONSE, Constraint.PRECEDENCE);

        //MR-I: produce Key-Value-Pairs
        Database db = eventLog.getTraces().stream().map(
                trace ->
                {
                    Database database = new Database();

                    List<Event> eventsA = trace.getEvents().stream().map(
                            event -> new Event(event.getAttributes().stream().filter(attribute -> dimensionsA.contains(attribute.getKey())).collect(Collectors.toList()))
                    ).collect(Collectors.toList());

                    List<Event> eventsB = trace.getEvents().stream().map(event ->
                            new Event(event.getAttributes().stream().filter(attribute -> dimensionsB.contains(attribute.getKey())).collect(Collectors.toList()))
                    ).collect(Collectors.toList());


                    List<String> tasksResponse = new ArrayList<>();
                    boolean breakPrecedence = false;
                    List<Event> uniqueEventsInTrace = new ArrayList<>();

                    for (int i = 0; i < trace.getEvents().size(); i++) {

                        /**
                         * ETA
                         */
                        database.addEta(eventsA.get(i), 1);
                        if (!args[0].equals(args[1])) {
                            database.addEta(eventsB.get(i), 1);
                        }

                        //for epsilon:
                        if (!uniqueEventsInTrace.contains(eventsA.get(i))) {
                            uniqueEventsInTrace.add(eventsA.get(i));
                        }
                        if (!uniqueEventsInTrace.contains(eventsB.get(i))) {
                            uniqueEventsInTrace.add(eventsB.get(i));
                        }


                        for (int j = i + 1; j < trace.getEvents().size(); j++) {

                            /**
                             * RESPONSE
                             */
                            if (!tasksResponse.contains(eventsB.get(j).toString())) {
                                database.addSigma(Constraint.RESPONSE, new Tuple<>(eventsA.get(i), eventsB.get(j)), 1);
                                tasksResponse.add(eventsB.get(j).toString());
                            }

                            /**
                             * PRECEDENCE
                             */
                            if(!breakPrecedence) {
                                database.addSigma(Constraint.PRECEDENCE, new Tuple<>(eventsB.get(i), eventsA.get(j)), 1);
                                if(eventsB.get(i).equals(eventsB.get(j)))
                                    breakPrecedence = true;
                            }
                        }
                        tasksResponse.clear();
                        breakPrecedence = false;
                    }

                    /**
                     * EPSILON
                     */
                    for (Event uniqueEvent : uniqueEventsInTrace) {
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
                     * SIGMA
                     */
                    for (Constraint constraint : consideredConstraints) {
                        for (Map.Entry<Tuple<Event>, Integer> currentEntry : currentDb.getSigmaEntry(constraint).entrySet()) {
                            Tuple<Event> currentTuple = currentEntry.getKey();
                            int currentDbValue = currentEntry.getValue();
                            accDb.addSigma(constraint, currentTuple, currentDbValue);
                        }
                    }
                    return accDb;
                }
        ).get();


        //'MR-II': calculate Support and Confidence
        Map<Event, Integer> eta = db.getEta();
        Map<Event, Integer> epsilon = db.getEpsilon();


        for (Constraint constraint : consideredConstraints) {

            System.out.println(constraint.name());
            for (Map.Entry<Tuple<Event>, Integer> currentEntry : db.getSigmaEntry(constraint).entrySet()) {

                switch(constraint)
                {
                    case RESPONSE:
                        Event currentEvent = currentEntry.getKey().x;
                        int currentEta = eta.get(currentEvent);
                        double support = currentEntry.getValue() / (double) currentEta;

                        int currentEpsilon = epsilon.get(currentEvent);
                        double confidence = support * (currentEpsilon / (double) eventLog.getTraces().size());

                        System.out.println("Support(" + currentEntry.getKey() + ") = \t\t" + support);
                        System.out.println("Confidence(" + currentEntry.getKey() + ") = \t" + confidence);
                        break;

                    case PRECEDENCE:
                        currentEvent = currentEntry.getKey().y;
                        currentEta = eta.get(currentEvent);
                        support = currentEntry.getValue() / (double) currentEta;

                        currentEpsilon = epsilon.get(currentEvent);
                        confidence = support * (currentEpsilon / (double) eventLog.getTraces().size());

                        System.out.println("Support(" + currentEntry.getKey() + ") = \t\t" + support);
                        System.out.println("Confidence(" + currentEntry.getKey() + ") = \t" + confidence);
                        break;
                }

            }
        }

    }

}
