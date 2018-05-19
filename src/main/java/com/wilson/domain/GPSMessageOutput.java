package com.wilson.domain;

import java.io.Serializable;

/**
 * 广告黑名单
 * @author Administrator
 *
 */
public class GPSMessageOutput implements Serializable {

    private static final long serialVersionUID = 1L;

    //terminalId_timeId,采集频次_总点数_在线点数_弱信号点数_GPS为未启点数_速度为0点数
    private String terminalId;
    private String timeId;
    private String frequency;
    private String totalNum;
    private String onlineNum;
    private String weakNum;
    private String notOpenGpsNum;
    private String zeroSpeedNum;
    private String offlineNum = "";
    private String NormalNum = "";
    private String onlinePercent = "";

    public String getOfflineNum() {
        return offlineNum;
    }

    public void setOfflineNum(String offlineNum) {
        this.offlineNum = offlineNum;
    }

    public String getNormalNum() {
        return NormalNum;
    }

    public void setNormalNum(String normalNum) {
        NormalNum = normalNum;
    }

    public String getOnlinePercent() {
        return onlinePercent;
    }

    public void setOnlinePercent(String onlinePercent) {
        this.onlinePercent = onlinePercent;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(String onlineNum) {
        this.onlineNum = onlineNum;
    }

    public String getWeakNum() {
        return weakNum;
    }

    public void setWeakNum(String weakNum) {
        this.weakNum = weakNum;
    }

    public String getNotOpenGpsNum() {
        return notOpenGpsNum;
    }

    public void setNotOpenGpsNum(String notOpenGpsNum) {
        this.notOpenGpsNum = notOpenGpsNum;
    }

    public String getZeroSpeedNum() {
        return zeroSpeedNum;
    }

    public void setZeroSpeedNum(String zeroSpeedNum) {
        this.zeroSpeedNum = zeroSpeedNum;
    }
}
