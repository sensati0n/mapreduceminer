package mapreduceminer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.springframework.boot.test.context.SpringBootTest;

import de.ubt.ai4.mapreduceminer.JobRunner;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.model.Trace;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.Configuration;
import mapreduceminer.service.HospitalLoader;
import mapreduceminer.service.XesLoader;

@SpringBootTest
public class MinerTest {
   
@Test
public void testMiner() throws Exception {

    EventLog eventLog = XesLoader.loadFromFile("C:\\Users\\bt304947\\Downloads\\Hospital_log.xes");

    List<Trace> allTraces = eventLog.getTraces();
    List<Trace> smallTraces = new ArrayList<>();

    for(int i = 0; i < 100; i++) {
        smallTraces.add(allTraces.get(i));
    }
    EventLog smallEventLog = new EventLog();
    smallEventLog.setTraces(smallTraces);
    int blabla =2;

 Configuration configuration = new Configuration();
 configuration
         .setEventIdentifier("concept:name")
         .setAdditionalAttribute("org:group")
         //.allConstraints()
         .addConstraint(Response.class)
         //.setAuxiliaryDatabaseClass(CustomAuxiliaryDatabase.class)
         .allConstraintTypes();


 JobRunner job = new JobRunner(smallEventLog, configuration);
 /*job.run();

 for(ResultElement mr : job.getMiningResult().getResults()) {

         System.out.println(mr.getTemplate() + "," + mr.getType() + "(" + mr.getEventA() + ", " + mr.getEventB() + ")" + "\t" + mr.getSupport() + "\t" + mr.getConfidence());

 }*/
}


}