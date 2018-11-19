package de.ubt.ai4.mapreduceminer.result;

import de.ubt.ai4.mapreduceminer.constraint.*;

import java.util.ArrayList;
import java.util.List;

public class MiningResult {

    private List<ResultElement> results = new ArrayList<>();
    public List<ResultElement> getResults() { return this.results; }
    public void setResults(List<ResultElement> results) { this.results = results; }

    public MiningResult() { }

    public void addResult(SingleEventConstraint constraint, double support, double confidence) {
        this.results.add(new ResultElement(constraint.getClass().toString(), constraint.getEvent(), support, confidence));
    }

    public void addResult(DoubleEventConstraint constraint, double support, double confidence) {
        this.results.add(new ResultElement(constraint.getClass().toString(), constraint.getEventTuple().x, constraint.getEventTuple().y, support, confidence));
    }

    public void addResult(IntEventConstraint constraint, double support, double confidence) {
        this.results.add(new ResultElement(constraint.getClass().toString(), constraint.getEvent(), constraint.getN(), support, confidence));
    }

    public void addResult(ResultElement resultElement) {
        this.results.add(resultElement);
    }
}
