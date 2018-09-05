package de.ai4.mapreduceminer;

import de.ai4.mapreduceminer.model.Attribute;
import de.ai4.mapreduceminer.model.Event;
import de.ai4.mapreduceminer.model.EventLog;
import de.ai4.mapreduceminer.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainTest {


    @Test
    public void filterEventsTest()
    {

        EventLog eventLog = TestHelper.buildEventLog();
        List<String> dimensions = Arrays.asList("task".split(","));


        /**
         *  deals.stream()
         *         .filter(deal -> deal.type() == Deal.Type.ELECTRONIC)
         *         .forEach(System.out::println)
         */

        /**
         List<Event> events = eventLog.getTraces().get(0).getEvents();

         events.stream().forEach(event ->
         {
         List<Attribute> attributes = event.getAttributes();
         event.setAttributes(attributes.stream().filter(attribute -> dimensions.contains(attribute.getKey())).collect(Collectors.toList()));
         });
*/

        List<Event> events = eventLog.getTraces().get(0).getEvents();

        events.stream().forEach(event -> event.setAttributes(event.getAttributes().stream().filter(attribute -> dimensions.contains(attribute.getKey())).collect(Collectors.toList())));



        int sum = 0;
    }
}
