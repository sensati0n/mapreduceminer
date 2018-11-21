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

         AuxiliaryDatabase.logInfo = new LogInfo(eventLog, configuration);

    }

    public Configuration getConfiguration() {
        return configuration;
    }

    private void mrii(Database db) {

        MiningResult result = new MiningResult();

        for (Class constraint : configuration.getConstraints()) {

            for (Map.Entry<Constraint, Integer> currentEntry : db.getSigmaEntry(constraint).entrySet()) {
                result.addResult(
                        currentEntry.getKey().getResult(db, currentEntry.getValue(), eventLog.getTraces().size()));
            }
        }
        this.miningResult = result;
    }

    public void run() {

        // MR-I: produce Key-Value-Pairs
        Database db = eventLog.getTraces().stream().map((trace) -> map(trace))
                .reduce((accDb, currentDb) -> reduce(accDb, currentDb)).get();


                for(Map.Entry<Tuple<Event>, Integer> curr : db.getTwoDimEpsilon().entrySet()) {
                    System.out.println("funcEPSILON: \t" + curr.getKey() + ", " + curr.getValue());

                }


        // 'MR-II': calculate Support and Confidence
        mrii(db);

    }

    private Constraint instantiate(Class<Constraint> c, Event eventA, Event eventB, int n, ConstraintType type) {
        System.out.println("instanciate:" + type + c.getSimpleName() + eventA + ", " + eventB);


       
        Constraint impl = null;
        try {
            impl = c.getConstructor().newInstance();

            if (impl instanceof DoubleEventConstraint) {

                if(impl instanceof HistoryBased)
                {
                 switch(type)
                 {
                    case ACTIVATION:
                    eventA = eventA.filter(this.getConfiguration().getEventIdentifier());
                    eventB = eventB.filter(this.getConfiguration().getEventIdentifier(), this.getConfiguration().getAdditionalAttribute());
                break;


                case TARGET:
                    eventA = eventA.filter(this.getConfiguration().getEventIdentifier(), this.getConfiguration().getAdditionalAttribute());
                    eventB = eventB.filter(this.getConfiguration().getEventIdentifier());
                break;
                  
                 }
                }
                     else {
     
                         switch(type)
                         {
                            case ACTIVATION:
                            eventA = eventA.filter(this.getConfiguration().getEventIdentifier(), this.getConfiguration().getAdditionalAttribute());
                            eventB = eventB.filter(this.getConfiguration().getEventIdentifier());
                        break;
        
        
                        case TARGET:
                            eventA = eventA.filter(this.getConfiguration().getEventIdentifier());
                            eventB = eventB.filter(this.getConfiguration().getEventIdentifier(), this.getConfiguration().getAdditionalAttribute());
                        break;
                  
                 }
             }

                ((DoubleEventConstraint) impl).setEventA(eventA);
                ((DoubleEventConstraint) impl).setEventB(eventB);
                ((DoubleEventConstraint) impl).setType(type);
            } else if (impl instanceof SingleEventConstraint) {
                switch(type)
                {
                    case ACTIVATION:
                                 eventA = eventA.filter(this.getConfiguration().getEventIdentifier(), this.getConfiguration().getAdditionalAttribute());
                                 eventB = eventB.filter(this.getConfiguration().getEventIdentifier());
                             break;
             
             
                             case TARGET:
                                 eventA = eventA.filter(this.getConfiguration().getEventIdentifier());
                                 eventB = eventB.filter(this.getConfiguration().getEventIdentifier(), this.getConfiguration().getAdditionalAttribute());
                             break;
                }
        
                ((SingleEventConstraint) impl).setEvent(eventA);
                ((SingleEventConstraint) impl).setType(type);
            } else if (impl instanceof IntEventConstraint) {
                switch(type)
                {
                    case ACTIVATION:
                                 eventA = eventA.filter(this.getConfiguration().getEventIdentifier(), this.getConfiguration().getAdditionalAttribute());
                                 eventB = eventB.filter(this.getConfiguration().getEventIdentifier());
                             break;
             
             
                             case TARGET:
                                 eventA = eventA.filter(this.getConfiguration().getEventIdentifier());
                                 eventB = eventB.filter(this.getConfiguration().getEventIdentifier(), this.getConfiguration().getAdditionalAttribute());
                             break;
                }
        
                ((IntEventConstraint) impl).setEvent(eventA);
                ((IntEventConstraint) impl).setN(n);
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

        AuxiliaryDatabase activationAD = new AuxiliaryDatabase();
        AuxiliaryDatabase targetAD = new AuxiliaryDatabase();
        try {
            activationAD = (AuxiliaryDatabase) getConfiguration().getAuxiliaryDatabaseClass().getConstructor().newInstance();
            targetAD =  (AuxiliaryDatabase) getConfiguration().getAuxiliaryDatabaseClass().getConstructor().newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        activationAD.first = events.get(0);
        activationAD.last = events.get(events.size() - 1);

        for (int i = 0; i < trace.getEvents().size(); i++) {
            System.out.println("i:"+i);

            activationAD.currentI = i;
            targetAD.currentI = i;

            activationAD.addToEventCounter(events.get(i));
            /**
             * ETA
             */
            database.addEta(events.get(i).filter(eventIdentifier), 1);
            if (!configuration.getAdditionalAttribute().isEmpty()) {
                database.addEta(events.get(i).filter(eventIdentifier, additionalAttribute), 1);
            }

            // for epsilon:
            if (!activationAD.uniqueEventsInTrace.contains(events.get(i).filter(eventIdentifier))) {
                activationAD.uniqueEventsInTrace.add(events.get(i).filter(eventIdentifier));
            }
            if (!activationAD.uniqueEventsInTrace
                    .contains(events.get(i).filter(eventIdentifier, additionalAttribute))) {
                activationAD.uniqueEventsInTrace.add(events.get(i).filter(eventIdentifier, additionalAttribute));
            }

            for (int j = 0; j < trace.getEvents().size(); j++) {
                System.out.println("j:"+j);
                activationAD.currentJ = j;
                targetAD.currentJ = j;

                if (j >= i+1) {
                    if (!(activationAD.uniqueEventCombinationInTrace.contains(new Tuple<>(events.get(i).filter(eventIdentifier), events.get(j).filter(eventIdentifier))))) {
                        activationAD.uniqueEventCombinationInTrace.add(new Tuple<>(events.get(i).filter(eventIdentifier), events.get(j).filter(eventIdentifier)));
                    }
                    if (!(activationAD.uniqueEventCombinationInTrace.contains(new Tuple<>(events.get(i).filter(eventIdentifier, additionalAttribute), events.get(j).filter(eventIdentifier, additionalAttribute))))) {
                        activationAD.uniqueEventCombinationInTrace.add(new Tuple<>(events.get(i).filter(eventIdentifier, additionalAttribute), events.get(j).filter(eventIdentifier, additionalAttribute)));
                    }
                }

              
                for (Class<Constraint> c : getConfiguration().getConstraints()) {
                    Constraint impl = instantiate(c, events.get(i), events.get(j), -1, ConstraintType.ACTIVATION);
                    Constraint implTarget = instantiate(c, events.get(i), events.get(j), -1,  ConstraintType.TARGET);

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
                     

            activationAD.clearEventBased();
            targetAD.clearEventBased();
        }

        for(Map.Entry<Event, Integer> entry : activationAD.eventCounter.entrySet()){
            for (Class<Constraint> c : getConfiguration().getConstraints()) {
                Constraint impl = instantiate(c, entry.getKey(), new Event(), entry.getValue(), ConstraintType.ACTIVATION);
                if (impl instanceof Tracebased) {
                    Tracebased traceBasedImpl = (Tracebased) impl;
                    if(traceBasedImpl.logic(activationAD, -1, events.size()))
                        database.addSigma(traceBasedImpl, 1);
    
                } else {
                    // No Eventbased Constraints are considered here.
                }
            }
        }

        for (Event uniqueEvent : activationAD.uniqueEventsInTrace) {
          database.addEpsilon(uniqueEvent, 1);

        }
        for (Tuple<Event> uniqueEventCombination : activationAD.uniqueEventCombinationInTrace) {
            database.addTwoDimEpsilon(uniqueEventCombination, 1);
          }

        pivotLogger.addPivotTraceInformation(pti);

        return database;

    }

    private Database reduce(Database currentDb, Database accDb) {

        // ETA
        for (Map.Entry<Event, Integer> currentEntry : currentDb.getEta().entrySet()) {
            int currentDbValue = currentEntry.getValue();
            accDb.addEta(currentEntry.getKey(), currentDbValue);
        }

        // EPSILON
        for (Map.Entry<Event, Integer> currentEntry : currentDb.getEpsilon().entrySet()) {
            int currentDbValue = currentEntry.getValue();
            accDb.addEpsilon(currentEntry.getKey(), currentDbValue);
        }
         // 2-dim EPSILON
         for (Map.Entry<Tuple<Event>, Integer> currentEntry : currentDb.getTwoDimEpsilon().entrySet()) {
            int currentDbValue = currentEntry.getValue();
            accDb.addTwoDimEpsilon(currentEntry.getKey(), currentDbValue);

        }

        // SIGMA
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
