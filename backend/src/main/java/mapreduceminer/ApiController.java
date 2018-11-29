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
     
  
        return "Greetings from Spring Boot!";
    }

}