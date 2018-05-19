package com.wilson.domain;

import java.io.Serializable;

/**
 * 广告黑名单
 * @author Administrator
 *
 */




public class GPSMessageOutput2 implements Serializable {

    private static final long serialVersionUID = 1L;
    //<terminalId_timeId,指标名_指标值>
    private String terminalId_timeId;
    private String targetName;
    private String targetValue;

    public String getTerminalId_timeId() {
        return terminalId_timeId;
    }

    public void setTerminalId_timeId(String terminalId_timeId) {
        this.terminalId_timeId = terminalId_timeId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }
}
