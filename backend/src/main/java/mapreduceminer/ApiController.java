package mapreduceminer;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mapreduceminer.model.*;
import mapreduceminer.service.MiningService;
import mapreduceminer.service.MiningServiceResult;

@RestController
public class ApiController {

    @GetMapping(path="/ok")
    public String getMethod() {
        return "ok";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllWeatherStations() {
        return "weatherService.getAllStations()";
    }

    @RequestMapping(path = "miningJob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE) 
    public ResponseEntity<MiningServiceResult> miningJob(@RequestBody EventLog webEventLog) {

    MiningServiceResult result = MiningService.miningJob(webEventLog);

    return ResponseEntity.ok(result);    
}


    @RequestMapping("/")
    public String index() {

        System.out.println("RUNNING TESTS");
     
        //EventLog eventLog = XesLoader.loadXes("C:\\Users\\bt304947\\Documents\\sampleXes.xes");
/*
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
        eventLog.setTraces(traces);

     Configuration configuration = new Configuration();
     configuration
             .setEventIdentifier("task")
             .setAdditionalAttribute("resource")
             .addConstraint(Response.class)
             .allConstraintTypes();

     JobRunner job = new JobRunner(eventLog, configuration);
     job.run();

     for(ResultElement mr : job.getMiningResult().getResults()) {
         System.out.println(mr.getTemplate() + "(" + mr.getEventA() + ", " + mr.getEventB() + ")" + "\t" + mr.getSupport() + "\t" + mr.getConfidence());
     }*/
        return "Greetings from Spring Boot!";
    }

}