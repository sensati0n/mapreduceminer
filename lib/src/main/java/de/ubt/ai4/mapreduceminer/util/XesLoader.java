package de.ubt.ai4.mapreduceminer.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


import de.ubt.ai4.mapreduceminer.model.Attribute;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.model.Trace;

public class XesLoader {

    static final String EVENTLOG = "log";
    static final String TRACE = "trace";
    static final String EVENT = "event";
    static final String ATTRIBUTE = "string";

    static final String EVENT_IDENTIFIER = "concept:name";
    static final String ADDITIONAL_ATTRIBUTE = "org:group";

    public static EventLog loadFromFile(String path) {

        EventLog eventLog = null;
        Trace trace = null;
        Event event = null;
        Attribute attribute = null;

        int traceCounter = 0;

        boolean isLogInformation = false;

        try {
          
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(path);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            while (eventReader.hasNext()) {
                XMLEvent xmlEvent = eventReader.nextEvent();

                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                
                                 

                    switch(startElement.getName().getLocalPart()) {

                        case EVENTLOG: //<log>
                            eventLog = new EventLog();
                        break;

                        case TRACE:     //<trace>
                            System.out.println("t: " + traceCounter++ );
                            trace = new Trace();
                        break;

                        case EVENT:     //<event>
                            isLogInformation = true;
                            event = new Event();
                        break;

                        case ATTRIBUTE: //<string>
                        if(!isLogInformation) {
                            continue;
                        }
                        if(startElement.getName().getLocalPart().equals(ATTRIBUTE)) {

                            attribute = new Attribute();

                            Iterator<javax.xml.stream.events.Attribute> attributes = startElement.getAttributes();
                            while(attributes.hasNext()) {
                                javax.xml.stream.events.Attribute xmlAttribute = attributes.next();
                                if(xmlAttribute.getName().toString().equals("key")) {
                                    attribute.setKey(xmlAttribute.getValue());
                                }
                                else if(xmlAttribute.getName().toString().equals("value")) {
                                    attribute.setValue(xmlAttribute.getValue());
                                }
                            }
                            event.addAttribute(attribute.getKey(), attribute.getValue());

                    }
                        break;
                    
                    default: //ignore
                    break;
                        
                       
                            
                    }
                }
                
                  if(xmlEvent.isEndElement()) {
                      
                    EndElement endElement = xmlEvent.asEndElement();
                        
                    switch(endElement.getName().getLocalPart()) {
                        case EVENTLOG: //</log>
                            
                        break;

                        case TRACE:     //</trace>
                            eventLog.addTrace(trace);
                        break;
                        case EVENT:     //</event>
                            trace.addEvent(event.filter(EVENT_IDENTIFIER, ADDITIONAL_ATTRIBUTE));
                        break;

                        default: //ignore
                        break;
                        }
                    
                }
                 
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return eventLog;

    }

}