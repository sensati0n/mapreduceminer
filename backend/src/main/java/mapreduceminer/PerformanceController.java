package mapreduceminer;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mapreduceminer.model.*;
import mapreduceminer.service.MiningService;
import mapreduceminer.service.MiningServiceResult;
import mapreduceminer.service.XesLoader;
import de.ubt.ai4.mapreduceminer.JobRunner;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.model.Trace;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.Configuration;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.model.EventLog;

@RestController
public class PerformanceController {

  
    @RequestMapping("/performance/hospital")
    public String index() {

        System.out.println("RUNNING TESTS");
        EventLog eventLog = XesLoader.loadFromFile("C:\\Users\\bt304947\\Downloads\\Hospital_log.xes");

        List<Trace> allTraces = eventLog.getTraces();
        List<Trace> smallTraces = new ArrayList<>();
    
        for(Trace t : allTraces) {
            if(t.getEvents().size()<50)
            {
                smallTraces.add(t);
            }
        }
        System.out.println("small Traces: " + smallTraces.size());

        EventLog smallEventLog = new EventLog();
        smallEventLog.setTraces(smallTraces);
        int blabla =2;
    
     Configuration configuration = new Configuration();
     configuration
             .setEventIdentifier("concept:name")
             .setAdditionalAttribute("org:group")
             .allConstraints()
             //.addConstraint(Response.class)
             //.setAuxiliaryDatabaseClass(CustomAuxiliaryDatabase.class)
             .allConstraintTypes();
    
    
     JobRunner job = new JobRunner(smallEventLog, configuration);
     job.run();
    
     for(ResultElement mr : job.getMiningResult().getResults()) {
    
        if(mr.getSupport() > 0.8 && mr.getConfidence() > 0.3)
             System.out.println(mr.getTemplate() + "," + mr.getType() + "(" + mr.getEventA() + ", " + mr.getEventB() + ")" + "\t" + mr.getSupport() + "\t" + mr.getConfidence());
    
     }
  
        return "Greetings from Spring Boot!";
    }

}