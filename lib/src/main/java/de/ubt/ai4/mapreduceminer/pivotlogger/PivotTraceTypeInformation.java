package de.ubt.ai4.mapreduceminer.pivotlogger;

public class PivotTraceTypeInformation {

    private Class constraint;

    public PivotList pivotListActivation = new PivotList(constraint, "activation");
    public PivotList pivotListTarget = new PivotList(constraint, "target");

    public PivotList getPivotListTarget() {
        return this.pivotListTarget;
    }

    public void setPivotListTarget(PivotList pivotListTarget) {
        this.pivotListTarget = pivotListTarget;
    }

    public PivotList getPivotListActivation() {
        return this.pivotListActivation;
    }

    public void setPivotListActivation(PivotList pivotListActivation) {
        this.pivotListActivation = pivotListActivation;
    }

    public PivotTraceTypeInformation(Class c) {
        this.constraint = c;
    }
}