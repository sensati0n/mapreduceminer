package de.ai4.mapreduceminer;

import de.ai4.mapreduceminer.model.Event;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DatabaseTest {


    @Test
    public void tupleTest()
    {

        Event e11 = new Event();
        e11.addAttribute("task", "a");
        Event e12 = new Event();
        e12.addAttribute("task", "b");

        Event e21 = new Event();
        e21.addAttribute("task", "a");
        Event e22 = new Event();
        e22.addAttribute("task", "b");

        Tuple<Event> t1 = new Tuple<>(e11, e12);
        Tuple<Event> t2 = new Tuple<>(e21, e22);

        assertTrue(t1.equals(t2));

    }

    @Test
    public void collectionTest()
    {
        Event e11 = new Event();
        e11.addAttribute("task", "a");
        Event e12 = new Event();
        e12.addAttribute("task", "b");

        Event e21 = new Event();
        e21.addAttribute("task", "a");
        Event e22 = new Event();
        e22.addAttribute("task", "b");

        //t1 equals t2
        Tuple<Event> t1 = new Tuple<>(e11, e12);
        Tuple<Event> t2 = new Tuple<>(e21, e22);

        Map<Tuple<Event>, Integer> hashmap = new HashMap<Tuple<Event>, Integer>();
        hashmap.put(t1, 1);

        Set<Tuple<Event>> keyset = hashmap.keySet();

        assertTrue(keyset.contains(t2));
    }
}
