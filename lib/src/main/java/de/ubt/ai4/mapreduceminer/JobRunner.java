package de.ubt.ai4.mapreduceminer;

import de.ubt.ai4.mapreduceminer.constraint.*;
import de.ubt.ai4.mapreduceminer.constraint.existence.Absence;
import de.ubt.ai4.mapreduceminer.constraint.existence.Existence;
import de.ubt.ai4.mapreduceminer.constraint.existence.Participation;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.model.Trace;
import de.ubt.ai4.mapreduceminer.pivotlogger.PivotLogger;
import de.ubt.ai4.mapreduceminer.pivotlogger.PivotTraceInformation;
import de.ubt.ai4.mapreduceminer.result.MiningResult;
import de.ubt.ai4.mapreduceminer.util.*;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class JobRunner {

    private EventLog eventLog;
    private Configuration configuration;

    private String eventIdentifier = "";
    private String additionalAttribute = "";

    private PivotLogger pivotLogger = new PivotLogger();
    private MiningResult miningResult;

    int counter = 1;

    public JobRunner(EventLog eventLog, Configuration configuration) {
        this.eventLog = eventLog;
        this.configuration = configuration;

        this.eventIdentifier = configuration.getEventIdentifier();
        this.additionalAttribute = configuration.getAdditionalAttribute();

        ConstraintImpl.setEventIdentifier(configuration.getEventIdentifier());
        ConstraintImpl.setAdditionalAttribute(configuration.getAdditionalAttribute());

        AuxilaryDatabase.logInfo = new LogInfo(eventLog, configuration);

    }

    public Configuration getConfiguration() {
        return configuration;
    }


    private void calcS(Database db) {

        MiningResult result = new MiningResult();

        for (Class constraint : configuration.getConstraints()) {

            for (Map.Entry<Constraint, Integer> currentEntry : db.getSigmaEntry(constraint).entrySet()) {
                result.addResult(currentEntry.getKey().getResult(db, currentEntry.getValue(), eventLog.getTraces().size()));
            }
        }
        this.miningResult = result;
    }

    public void run() {

        //MR-I: produce Key-Value-Pairs
        Database db = eventLog.getTraces().stream()
                .parallel().map((trace) -> map(trace))
                .reduce((accDb, currentDb) -> reduce(accDb, currentDb))
                .get();

        //'MR-II': calculate Support and Confidence
        calcS(db);

    }

    private Constraint instantiate(Class<Constraint> c, Event eventA, Event eventB, ConstraintType type) {

        Constraint impl = null;
        try {
            impl = c.getConstructor().newInstance();

            if (impl instanceof DoubleEventConstraint) {
                ((DoubleEventConstraint) impl).setEventA(eventA);
                ((DoubleEventConstraint) impl).setEventB(eventB);
                ((DoubleEventConstraint) impl).setType(type);
            } else if (impl instanceof SingleEventConstraint) {
                ((SingleEventConstraint) impl).setEvent(eventA);
                ((SingleEventConstraint) impl).setType(type);
            } else if (impl instanceof IntEventConstraint) {
                ((IntEventConstraint) impl).setEvent(eventA);
                ((IntEventConstraint) impl).setType(type);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return impl;

    }

    private Database map(Trace trace) {
        System.out.println(counter++ + " \t /" + eventLog.getTraces().size());
        List<Event> events = trace.getEvents();

        PivotTraceInformation pti = new PivotTraceInformation(trace, configuration);

        Database database = new Database(configuration);

        AuxilaryDatabase activationAD = new AuxilaryDatabase();
        AuxilaryDatabase targetAD = new AuxilaryDatabase();
        AuxilaryDatabase correlationAD = new AuxilaryDatabase();


        activationAD.first = events.get(0);
        activationAD.last = events.get(events.size() - 1);


        for (int i = 0; i < trace.getEvents().size(); i++) {

            /**
             * ETA
             */
            database.addEta(events.get(i).filter(eventIdentifier), 1);
            if (!configuration.getAdditionalAttribute().isEmpty()) {
                database.addEta(events.get(i).filter(eventIdentifier, additionalAttribute), 1);
            }

            //for epsilon:
            if (!activationAD.uniqueEventsInTrace.contains(events.get(i).filter(eventIdentifier))) {
                activationAD.uniqueEventsInTrace.add(events.get(i).filter(eventIdentifier));
            }
            if (!activationAD.uniqueEventsInTrace.contains(events.get(i).filter(eventIdentifier, additionalAttribute))) {
                activationAD.uniqueEventsInTrace.add(events.get(i).filter(eventIdentifier, additionalAttribute));
            }


            for (int j = i + 1; j < trace.getEvents().size(); j++) {
                for (Class<Constraint> c : getConfiguration().getConstraints()) {
                    Constraint impl = instantiate(c, events.get(i), events.get(j), ConstraintType.ACTIVATION);
                    Constraint implTarget = instantiate(c, events.get(i), events.get(j), ConstraintType.TARGET);

                       /* if(impl instanceof Tracebased) {
                            Tracebased traceBasedImpl = (Tracebased) impl;
                            Tracebased traceBasedImplTarget = (Tracebased) implTarget;
                            for(Tracebased k : traceBasedImpl.logic(activationAD))
                                database.addSigma(k, 1);
*/
                    //    for(Tracebased k : traceBasedImplTarget.logic(targetAD))
                    //        database.addSigma(traceBasedImplTarget, 1);
                    //      }
                    //     else
                    if (impl instanceof Eventbased) {
                        Eventbased eventBasedImpl = (Eventbased) impl;
                        Eventbased eventBasedImplTarget = (Eventbased) implTarget;
                        if (eventBasedImpl.logic(activationAD))
                            database.addSigma(eventBasedImpl, 1);
                        else
                            pti.addPivot(impl.getClass(), i, j, "activation");

                        if (eventBasedImplTarget.logic(targetAD))
                            database.addSigma(implTarget, 1);
                        else
                            pti.addPivot(implTarget.getClass(), i, j, "target");
                    }
                }
            }
            activationAD.clear();
            targetAD.clear();
        }

        for (int k = 0; k < trace.getEvents().size(); k++) {
            /**
             * EPSILON
             */
            if (activationAD.uniqueEventsInTrace.contains(events.get(k)))
                database.addEpsilon(events.get(k), 1);

            for (Class<Constraint> c : getConfiguration().getConstraints()) {
                Constraint impl = instantiate(c, events.get(k), null, ConstraintType.ACTIVATION);

                if (impl instanceof Tracebased) {
                    Tracebased traceBasedImpl = (Tracebased) impl;
                    for (Tracebased t : traceBasedImpl.logic(activationAD, k, events.size())) {
                        database.addSigma(t, 1);
                    }
                }
            }
        }
        pivotLogger.addPivotTraceInformation(pti);

        return database;

    }


    private Database reduce(Database currentDb, Database accDb) {

        //ETA
        for (Map.Entry<Event, Integer> currentEntry : currentDb.getEta().entrySet()) {
            int currentDbValue = currentEntry.getValue();
            accDb.addEta(currentEntry.getKey(), currentDbValue);
        }

        //EPSILON
        for (Map.Entry<Event, Integer> currentEntry : currentDb.getEpsilon().entrySet()) {
            int currentDbValue = currentEntry.getValue();
            accDb.addEpsilon(currentEntry.getKey(), currentDbValue);
        }

        //SIGMA
        for (Class constraint : configuration.getConstraints()) {
            for (Map.Entry<Constraint, Integer> currentEntry : currentDb.getSigmaEntry(constraint).entrySet()) {
                Constraint currentTuple = currentEntry.getKey();
                int currentDbValue = currentEntry.getValue();
                accDb.addSigma(currentTuple, currentDbValue);
            }
        }
        return accDb;
    }

    public MiningResult getMiningResult() {
        return miningResult;
    }

    public PivotLogger getPivotLogger() {
        return pivotLogger;
    }

}
