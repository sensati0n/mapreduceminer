package de.ubt.ai4.mapreduceminer.util;

public class Tuple<Event> {
    public  Event x;
    public  Event y;


    public Tuple(Event x, Event y) {
        this.x = x;
        this.y = y;
    }

    public Tuple() {}

    @Override
    public boolean equals(Object o) {
        Tuple<Event> other = (Tuple<Event>) o;
/*
        if(other.x.equals(this.x) && other.y.equals(this.y))
            return true;
*/

        if(
            (other.x.equals(this.x) && other.y.equals(this.y))
            ||
            (other.x.equals(this.y) && other.y.equals(this.x))
            )
        return true;
        return false;
    }

    @Override
    public int hashCode() {
        return x.hashCode()^y.hashCode();
    }

    @Override
    public String toString() {
        return x.toString() + "|" + y.toString();
    }
}