package de.ubt.ai4.mapreduceminer.pivotlogger;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PivotLogger {

    private List<PivotTraceInformation> pivotInformation;

    public PivotLogger() {
        this.pivotInformation = new ArrayList<>();
    }

    public void addPivotTraceInformation(PivotTraceInformation pti) {
        pti.finalize();
        this.pivotInformation.add(pti);
    }

    public List<PivotTraceInformation> getPivotInformation() { return this.pivotInformation; }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        /*
        for(String pivot : pivotInformation)
            sb.append(pivot + "\n");

        */

        return sb.toString();
    }



}
