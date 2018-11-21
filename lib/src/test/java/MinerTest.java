import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.ubt.ai4.mapreduceminer.JobRunner;
import de.ubt.ai4.mapreduceminer.constraint.custom.WithinFiveSteps;
import de.ubt.ai4.mapreduceminer.constraint.existence.AtMostOne;
import de.ubt.ai4.mapreduceminer.constraint.existence.End;
import de.ubt.ai4.mapreduceminer.constraint.existence.Init;
import de.ubt.ai4.mapreduceminer.constraint.existence.Participation;
import de.ubt.ai4.mapreduceminer.constraint.mutualrelation.AlternateSuccession;
import de.ubt.ai4.mapreduceminer.constraint.mutualrelation.ChainSuccession;
import de.ubt.ai4.mapreduceminer.constraint.mutualrelation.CoExistence;
import de.ubt.ai4.mapreduceminer.constraint.mutualrelation.Succession;
import de.ubt.ai4.mapreduceminer.constraint.negativerelation.NotChainSuccession;
import de.ubt.ai4.mapreduceminer.constraint.negativerelation.NotCoExistence;
import de.ubt.ai4.mapreduceminer.constraint.negativerelation.NotSuccession;
import de.ubt.ai4.mapreduceminer.constraint.relation.AlternatePrecedence;
import de.ubt.ai4.mapreduceminer.constraint.relation.AlternateResponse;
import de.ubt.ai4.mapreduceminer.constraint.relation.ChainPrecedence;
import de.ubt.ai4.mapreduceminer.constraint.relation.ChainResponse;
import de.ubt.ai4.mapreduceminer.constraint.relation.Precedence;
import de.ubt.ai4.mapreduceminer.constraint.relation.RespondedExistence;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.model.Trace;
import de.ubt.ai4.mapreduceminer.pivotlogger.PivotElement;
import de.ubt.ai4.mapreduceminer.pivotlogger.PivotList;
import de.ubt.ai4.mapreduceminer.pivotlogger.PivotLogger;
import de.ubt.ai4.mapreduceminer.pivotlogger.PivotTraceInformation;
import de.ubt.ai4.mapreduceminer.pivotlogger.PivotTraceTypeInformation;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.Configuration;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.util.CustomAuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.Tuple;

public class MinerTest {

    @Test
    public void precedenceTest() {

        Precedence p = new Precedence();
        Precedence p2 = new Precedence();


        Event e00 = new Event();
        e00.addAttribute("task", "a");
        e00.addAttribute("resource", "x");
        Event e01 = new Event();
        e01.addAttribute("task", "c");
        e01.addAttribute("resource", "x");

        Event e10 = new Event();
        e10.addAttribute("task", "a");
        e10.addAttribute("resource", "x");
        Event e11 = new Event();
        e11.addAttribute("task", "c");
        e11.addAttribute("resource", "x");


        p.setEventA(e00);
        p.setEventB(e01);
        p.setType(ConstraintType.ACTIVATION);

        p2.setEventA(e00);
        p2.setEventB(e01);
        p2.setType(ConstraintType.ACTIVATION);

        System.out.println("PrecedenceTest " + p.equals(p2));


    }



    @Test
    public void tupleTest() {
        Event e00 = new Event();
        e00.addAttribute("task", "a");
        e00.addAttribute("resource", "x");
        Event e01 = new Event();
        e01.addAttribute("task", "c");
        e01.addAttribute("resource", "x");

        Event e10 = new Event();
        e10.addAttribute("task", "c");
        e10.addAttribute("resource", "x");
        Event e11 = new Event();
        e11.addAttribute("task", "a");
        e11.addAttribute("resource", "x");

        Tuple<Event> t1 = new Tuple<>(e00, e01);
        Tuple<Event> t2 = new Tuple<>(e10, e11);

        System.out.println(t1.equals(t2));


    }


@Test 
public void testCustomConstraintFulfill() {

    EventLog eventLog = new EventLog();

        List<Trace> traces = new ArrayList<>();

        Trace t1 = new Trace();
        Event e10 = new Event();
        e10.addAttribute("task", "impl");
        e10.addAttribute("resource", "x");
        Event e11 = new Event();
        e11.addAttribute("task", "test");
        e11.addAttribute("resource", "x");
        Event e12 = new Event();
        e12.addAttribute("task", "test");
        e12.addAttribute("resource", "y");
        Event e13 = new Event();
        e13.addAttribute("task", "final-test");
        e13.addAttribute("resource", "STE");
        Event e14 = new Event();
        e14.addAttribute("task", "deliver");
        e14.addAttribute("resource", "john");
        t1.addEvent(e10);
        t1.addEvent(e11);
        t1.addEvent(e12);
        t1.addEvent(e13);
        t1.addEvent(e14);

      traces.add(t1);

      eventLog.setTraces(traces);

      Configuration configuration = new Configuration();
      configuration
              .setEventIdentifier("task")
              .setAdditionalAttribute("resource")
              .addConstraint(WithinFiveSteps.class)
              .setAuxiliaryDatabaseClass(CustomAuxiliaryDatabase.class)
              .allConstraintTypes();


      JobRunner job = new JobRunner(eventLog, configuration);
      job.run();

      for(ResultElement mr : job.getMiningResult().getResults()) {

        if(mr.getEventA().equals(e11.filter("task")) && mr.getEventB().equals(e13))  {
            assertEquals(1.0d, mr.getSupport(), 0.01);
        }


        }
}



@Test 
public void testCustomConstraint() {

    EventLog eventLog = new EventLog();

        List<Trace> traces = new ArrayList<>();

        Trace t0 = new Trace();
        Event e00 = new Event();
        e00.addAttribute("task", "impl");
        e00.addAttribute("resource", "x");
        Event e01 = new Event();
        e01.addAttribute("task", "test");
        e01.addAttribute("resource", "z");
        Event e02 = new Event();
        e02.addAttribute("task", "test");
        e02.addAttribute("resource", "y");
        Event e03 = new Event();
        e03.addAttribute("task", "test");
        e03.addAttribute("resource", "x");
        Event e04 = new Event();
        e04.addAttribute("task", "test");
        e04.addAttribute("resource", "z");
        Event e05 = new Event();
        e05.addAttribute("task", "test");
        e05.addAttribute("resource", "y");
        Event e06 = new Event();
        e06.addAttribute("task", "test");
        e06.addAttribute("resource", "x");
        Event e07 = new Event();
        e07.addAttribute("task", "final-test");
        e07.addAttribute("resource", "STE");
        Event e08 = new Event();
        e08.addAttribute("task", "deliver");
        e08.addAttribute("resource", "job");
        t0.addEvent(e00);
        t0.addEvent(e01);
        t0.addEvent(e02);
        t0.addEvent(e03);
        t0.addEvent(e04);
        t0.addEvent(e05);
        t0.addEvent(e06);
        t0.addEvent(e07);
        t0.addEvent(e08);
        traces.add(t0);


      traces.add(t0);

      eventLog.setTraces(traces);

      Configuration configuration = new Configuration();
      configuration
              .setEventIdentifier("task")
              .setAdditionalAttribute("resource")
              .addConstraint(WithinFiveSteps.class)
              .setAuxiliaryDatabaseClass(CustomAuxiliaryDatabase.class)
              .allConstraintTypes();


      JobRunner job = new JobRunner(eventLog, configuration);
      job.run();

      for(ResultElement mr : job.getMiningResult().getResults()) {

        if(mr.getEventA().equals(e01.filter("task")) && mr.getEventB().equals(e07))  {
            assertNotEquals(1.0d, mr.getSupport(), 0.01);
        }


        }
}



    @Test
    public void test() {
       
        System.out.println("RUNNING TESTS");
     
           //EventLog eventLog = XesLoader.loadXes("C:\\Users\\bt304947\\Documents\\sampleXes.xes");

           EventLog eventLog = new EventLog();

           List<Trace> traces = new ArrayList<>();
           Trace t0 = new Trace();
           Event e00 = new Event();
           e00.addAttribute("task", "a");
           e00.addAttribute("resource", "x");
           Event e01 = new Event();
           e01.addAttribute("task", "c");
           e01.addAttribute("resource", "z");
           Event e02 = new Event();
           e02.addAttribute("task", "b");
           e02.addAttribute("resource", "y");
           Event e03 = new Event();
           e03.addAttribute("task", "b");
           e03.addAttribute("resource", "x");
           Event e04 = new Event();
           e04.addAttribute("task", "d");
           e04.addAttribute("resource", "z");
           Event e05 = new Event();
           e05.addAttribute("task", "b");
           e05.addAttribute("resource", "y");
           Event e06 = new Event();
           e06.addAttribute("task", "a");
           e06.addAttribute("resource", "x");
           t0.addEvent(e00);
           t0.addEvent(e01);
           t0.addEvent(e02);
           t0.addEvent(e03);
           t0.addEvent(e04);
           t0.addEvent(e05);
           t0.addEvent(e06);
           traces.add(t0);

           Trace t1 = new Trace();
           Event e10 = new Event();
           e10.addAttribute("task", "a");
           e10.addAttribute("resource", "x");
           Event e11 = new Event();
           e11.addAttribute("task", "b");
           e11.addAttribute("resource", "x");
           Event e12 = new Event();
           e12.addAttribute("task", "b");
           e12.addAttribute("resource", "y");
           Event e13 = new Event();
           e13.addAttribute("task", "c");
           e13.addAttribute("resource", "x");
         
           t1.addEvent(e10);
           t1.addEvent(e11);
           t1.addEvent(e12);
           t1.addEvent(e13);
      
           traces.add(t1);

           Trace t2 = new Trace();
           Event e20 = new Event();
           e20.addAttribute("task", "a");
           e20.addAttribute("resource", "y");
           Event e21 = new Event();
           e21.addAttribute("task", "c");
           e21.addAttribute("resource", "x");
           Event e22 = new Event();
           e22.addAttribute("task", "d");
           e22.addAttribute("resource", "y");

           t2.addEvent(e20);
           t2.addEvent(e21);
           t2.addEvent(e22);
         
           traces.add(t2);

         


           eventLog.setTraces(traces);

        Configuration configuration = new Configuration();
        configuration
                .setEventIdentifier("task")
                .setAdditionalAttribute("resource")
                .allConstraints()
                .setAuxiliaryDatabaseClass(CustomAuxiliaryDatabase.class)
                .allConstraintTypes();


        JobRunner job = new JobRunner(eventLog, configuration);
        job.run();

        for(ResultElement mr : job.getMiningResult().getResults()) {

                System.out.println(mr.getTemplate() + "," + mr.getType() + "(" + mr.getEventA() + ", " + mr.getEventB() + ")" + "\t" + mr.getSupport() + "\t" + mr.getConfidence());

        }

        PivotLogger logger = job.getPivotLogger();
        List<PivotTraceInformation> ptis = logger.getPivotInformation();
        for(PivotTraceInformation pti : ptis){
            List<PivotTraceTypeInformation> pttis = pti.getPtti();
            for(PivotTraceTypeInformation ptti : pttis) {
                PivotList pl = ptti.getPivotListActivation();
                List<PivotElement> pes = pl.getPivots();
                for(PivotElement pe : pes) {
                    System.out.println("Heyho" + pe.getI() + pe.getJ());
                }
            }
        }

    }

    
}