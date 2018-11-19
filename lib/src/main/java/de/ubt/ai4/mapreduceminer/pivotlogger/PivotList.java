package de.ubt.ai4.mapreduceminer.pivotlogger;

import java.util.ArrayList;
import java.util.List;

public class PivotList {

    private List<PivotElement> pivots;

    private Class constraint;

    private String type;

    public PivotList() {
        this.pivots = new ArrayList<>();
    }

    public PivotList(Class constraint, String type) {
        this.pivots = new ArrayList<>();

        this.constraint = constraint;
        this.type = type;
    }

    public void addPivot(PivotElement element) {
        this.pivots.add(element);
    }

    public List<PivotElement> getPivots() {
        return pivots;
    }

    public void setPivots(List<PivotElement> pivots) {
        this.pivots = pivots;
    }

    public Class getConstraint() {
        return constraint;
    }

    public void setConstraint(Class constraint) {
        this.constraint = constraint;
    }

}
