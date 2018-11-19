package de.ubt.ai4.mapreduceminer;

import de.ubt.ai4.mapreduceminer.constraint.existence.*;
import de.ubt.ai4.mapreduceminer.constraint.relation.RespondedExistence;
import de.ubt.ai4.mapreduceminer.constraint.relation.Response;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.pivotlogger.PivotLogger;
import de.ubt.ai4.mapreduceminer.result.MiningResult;
import de.ubt.ai4.mapreduceminer.util.Configuration;

import java.io.File;


public class Main {

    public static void main(String[] args) {
/*
       if(args.length < 1) {
            System.err.println("Usage: java Main.java xesFile dimensionsA dimensionsB ");
            System.exit(1);
        }
       
        
           EventLog eventLog = XesLoader.loadXes("C:\\Users\\bt304947\\Documents\\sampleXes.xes");

        Configuration configuration = new Configuration();
        configuration
                .setEventIdentifier("task")
                .setAdditionalAttribute("resource")
                .addConstraint(Response.class)
                .allConstraintTypes();

        JobRunner job = new JobRunner(eventLog, configuration);
        job.run();


        try {

            File file = new File("C:\\Users\\bt304947\\Documents\\result.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(MiningResult.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(job.getMiningResult(), file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        try {

            File file = new File("C:\\Users\\bt304947\\Documents\\result_pivot.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(PivotLogger.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);

            jaxbMarshaller.marshal(job.getPivotLogger(), file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

*/
    }

}
