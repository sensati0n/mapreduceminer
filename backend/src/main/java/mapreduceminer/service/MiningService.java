package mapreduceminer.service;

import org.springframework.stereotype.Service;

import de.ubt.ai4.mapreduceminer.JobRunner;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.*;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.Configuration;

@Service
public class MiningService {

    public static MiningServiceResult miningJob(mapreduceminer.model.EventLog webEventLog) {
        
        Configuration configuration = new Configuration();
        configuration
                .setEventIdentifier("task")
                .setAdditionalAttribute("resource")
                .allConstraints()
                .addConstraint(WithinFiveSteps.class)
                .setAuxiliaryDatabaseClass(CustomAuxiliaryDatabase.class)
                .activationConstraints();
   

        EventLog eventLog = convert(webEventLog);

        JobRunner job = new JobRunner(eventLog, configuration);
        job.run();

        MiningServiceResult result = new MiningServiceResult();
        result.setMiningResult(job.getMiningResult());
        result.setPivotLogger(job.getPivotLogger());
   
        for(ResultElement mr : job.getMiningResult().getResults()) {
            System.out.println(mr.getTemplate() + "(" + mr.getEventA() + ", " + mr.getEventB() + ")" + "\t" + mr.getSupport() + "\t" + mr.getConfidence());
        }

        return result;
    }

    private static EventLog convert(mapreduceminer.model.EventLog webEventLog) {
        EventLog minerEventLog = new EventLog();
    
        for(mapreduceminer.model.Trace webTrace : webEventLog.getTrace()) {
            Trace minerTrace = new de.ubt.ai4.mapreduceminer.model.Trace();
            for(mapreduceminer.model.Event webEvent : webTrace.getEvent()) {
                de.ubt.ai4.mapreduceminer.model.Event minerEvent = new de.ubt.ai4.mapreduceminer.model.Event();
                
                for(mapreduceminer.model.Attribute webAttribute : webEvent.getString()) {
                    minerEvent.addAttribute(webAttribute.getKey(), webAttribute.getValue());
                }
                minerTrace.addEvent(minerEvent);
            }
            minerEventLog.addTrace(minerTrace);
    
        }

        return minerEventLog;
    }
}