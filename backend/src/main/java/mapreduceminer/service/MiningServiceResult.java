package mapreduceminer.service;

import de.ubt.ai4.mapreduceminer.pivotlogger.PivotLogger;
import de.ubt.ai4.mapreduceminer.result.MiningResult;

public class MiningServiceResult {

    private MiningResult miningResult;
    public MiningResult getMiningResult() { return this.miningResult; }
    public void setMiningResult(MiningResult miningResult) { this.miningResult = miningResult; }

    private PivotLogger pivotLogger;
    public PivotLogger getPivotLogger() { return this.pivotLogger; }
    public void setPivotLogger(PivotLogger pivotLogger) { this.pivotLogger = pivotLogger; }
}
