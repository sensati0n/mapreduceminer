package de.ai4.mapreduceminer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {

    @Test
    public void eventEqualsTest()
    {
        Event e1 = new Event();
        e1.addAttribute("task", "a");
        Event e2 = new Event();
        e2.addAttribute("task", "a");

        assertTrue(e1.equals(e2));

        Event e3 = new Event();
        e3.addAttribute("task", "a");
        Event e4 = new Event();
        e4.addAttribute("task", "b");

        assertTrue(!e3.equals(e4));

        Event e5 = new Event();
        e5.addAttribute("task", "a");
        e5.addAttribute("resource", "x");
        Event e6 = new Event();
        e6.addAttribute("task", "a");
        e6.addAttribute("resource", "x");

        assertTrue(e5.equals(e6));

        Event e7 = new Event();
        e7.addAttribute("task", "a");
        e7.addAttribute("resource", "x");
        Event e8 = new Event();
        e8.addAttribute("task", "a");
        e8.addAttribute("resource", "y");

        assertTrue(!e7.equals(e8));

        Event e9 = new Event();
        e9.addAttribute("task", "a");
        e9.addAttribute("resource", "x");
        Event e10 = new Event();
        e10.addAttribute("resource", "x");
        e10.addAttribute("task", "a");


        assertTrue(!e9.equals(e10));

    }

}
