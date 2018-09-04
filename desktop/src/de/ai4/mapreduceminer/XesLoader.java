package de.ai4.mapreduceminer;

import de.ai4.mapreduceminer.model.EventLog;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XesLoader {

    public static EventLog loadXes(File xesFile) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(EventLog.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        EventLog eventLog = (EventLog) unmarshaller.unmarshal(xesFile);

        return eventLog;
    }
}
