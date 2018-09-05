package de.ai4.mapreduceminer;

import de.ai4.mapreduceminer.model.Attribute;
import de.ai4.mapreduceminer.model.Event;
import de.ai4.mapreduceminer.model.EventLog;
import de.ai4.mapreduceminer.model.Trace;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XesLoaderTest {

    @Test
    public void loadXesTest() throws JAXBException {

        File xesFile = new File("C:\\Users\\bt304947\\Documents\\sampleXes.xes");
        EventLog eventLog = XesLoader.loadXes(xesFile);

        int sum = 0;

    }

    @Test
    public void writeXesTest() throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(EventLog.class);

        List<Attribute> trace0event0attributes = Arrays.asList(new Attribute("task", "a"), new Attribute("resource", "x"));
        List<Attribute> trace0event1attributes = Arrays.asList(new Attribute("task", "c"), new Attribute("resource", "z"));
        List<Attribute> trace0event2attributes = Arrays.asList(new Attribute("task", "b"), new Attribute("resource", "y"));
        List<Attribute> trace0event3attributes = Arrays.asList(new Attribute("task", "b"), new Attribute("resource", "x"));
        List<Attribute> trace0event4attributes = Arrays.asList(new Attribute("task", "d"), new Attribute("resource", "z"));
        List<Attribute> trace0event5attributes = Arrays.asList(new Attribute("task", "b"), new Attribute("resource", "y"));
        List<Attribute> trace0event6attributes = Arrays.asList(new Attribute("task", "a"), new Attribute("resource", "x"));

        List<Event> trace0events = new ArrayList<>();

        trace0events.add(new Event(trace0event0attributes));
        trace0events.add(new Event(trace0event1attributes));
        trace0events.add(new Event(trace0event2attributes));
        trace0events.add(new Event(trace0event3attributes));
        trace0events.add(new Event(trace0event4attributes));
        trace0events.add(new Event(trace0event5attributes));
        trace0events.add(new Event(trace0event6attributes));

        Trace trace0 = new Trace(trace0events);


        List<Attribute> trace1event0attributes = Arrays.asList(new Attribute("task", "a"), new Attribute("resource", "x"));
        List<Attribute> trace1event1attributes = Arrays.asList(new Attribute("task", "b"), new Attribute("resource", "x"));
        List<Attribute> trace1event2attributes = Arrays.asList(new Attribute("task", "b"), new Attribute("resource", "y"));
        List<Attribute> trace1event3attributes = Arrays.asList(new Attribute("task", "c"), new Attribute("resource", "x"));


        List<Event> trace1events = new ArrayList<>();

        trace1events.add(new Event(trace1event0attributes));
        trace1events.add(new Event(trace1event1attributes));
        trace1events.add(new Event(trace1event2attributes));
        trace1events.add(new Event(trace1event3attributes));

        Trace trace1 = new Trace(trace1events);


        EventLog eventLog = new EventLog();

        eventLog.addTrace(trace0);
        eventLog.addTrace(trace1);

        int sum = 0;


        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(eventLog, System.out);
    }


    @Test
    public void writeSinglePerspectiveXesTest() throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(EventLog.class);

        List<Attribute> trace0event0attributes = Arrays.asList(new Attribute("task", "a"));
        List<Attribute> trace0event1attributes = Arrays.asList(new Attribute("task", "c"));
        List<Attribute> trace0event2attributes = Arrays.asList(new Attribute("task", "b"));
        List<Attribute> trace0event3attributes = Arrays.asList(new Attribute("task", "b"));
        List<Attribute> trace0event4attributes = Arrays.asList(new Attribute("task", "d"));
        List<Attribute> trace0event5attributes = Arrays.asList(new Attribute("task", "b"));
        List<Attribute> trace0event6attributes = Arrays.asList(new Attribute("task", "a"));

        List<Event> trace0events = new ArrayList<>();

        trace0events.add(new Event(trace0event0attributes));
        trace0events.add(new Event(trace0event1attributes));
        trace0events.add(new Event(trace0event2attributes));
        trace0events.add(new Event(trace0event3attributes));
        trace0events.add(new Event(trace0event4attributes));
        trace0events.add(new Event(trace0event5attributes));
        trace0events.add(new Event(trace0event6attributes));

        Trace trace0 = new Trace(trace0events);


        List<Attribute> trace1event0attributes = Arrays.asList(new Attribute("task", "a"));
        List<Attribute> trace1event1attributes = Arrays.asList(new Attribute("task", "b"));
        List<Attribute> trace1event2attributes = Arrays.asList(new Attribute("task", "b"));
        List<Attribute> trace1event3attributes = Arrays.asList(new Attribute("task", "c"));


        List<Event> trace1events = new ArrayList<>();

        trace1events.add(new Event(trace1event0attributes));
        trace1events.add(new Event(trace1event1attributes));
        trace1events.add(new Event(trace1event2attributes));
        trace1events.add(new Event(trace1event3attributes));

        Trace trace1 = new Trace(trace1events);


        EventLog eventLog = new EventLog();

        eventLog.addTrace(trace0);
        eventLog.addTrace(trace1);

        int sum = 0;


        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(eventLog, System.out);
    }
}
