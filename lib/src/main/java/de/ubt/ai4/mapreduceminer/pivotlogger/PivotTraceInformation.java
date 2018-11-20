package de.ubt.ai4.mapreduceminer.pivotlogger;

import de.ubt.ai4.mapreduceminer.util.Configuration;
import de.ubt.ai4.mapreduceminer.model.Trace;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class PivotTraceInformation {

    private final Trace trace;

    private List<PivotTraceTypeInformation> ptti;

    public List<PivotTraceTypeInformation> getPtti() {
        return this.ptti;
    }

    public void setPtti(List<PivotTraceTypeInformation> ptti) {
        this.ptti = ptti;
    }

    private Map<Class, PivotTraceTypeInformation> pttiHelperMap = new HashMap<>();

    public PivotTraceInformation(Trace trace, Configuration configuration) {

        this.ptti = new ArrayList<>();
        this.trace = trace;

        for(Class c : configuration.getConstraints()) {

            this.pttiHelperMap.put(c, new PivotTraceTypeInformation(c));


        }
    }

    public void addPivot(Class c, int i, int j, String type) {
        switch(type){
            case "activation": this.pttiHelperMap.get(c).pivotListActivation.addPivot(new PivotElement(i, j)); break;
            case "target": this.pttiHelperMap.get(c).pivotListTarget.addPivot(new PivotElement(i, j)); break;
        }
    }

    public void finalize() {
        for(Map.Entry<Class, PivotTraceTypeInformation> e : pttiHelperMap.entrySet())
            ptti.add(e.getValue());
    }

    public Trace getTrace()
    {
        return this.trace;
    }

}
