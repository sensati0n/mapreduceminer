package mapreduceminer.service;

import org.springframework.stereotype.Service;

import de.ubt.ai4.mapreduceminer.JobRunner;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.Configuration;

@Service
public class MiningService {

    public static MiningServiceResult miningJob(EventLog eventLog) {
        
        Configuration configuration = new Configuration();
        configuration
                .setEventIdentifier("task")
                .setAdditionalAttribute("resource")
                .addConstraint(Response.class)
                .allConstraintTypes();
   
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
}