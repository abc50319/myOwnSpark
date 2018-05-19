package com.wilson.dategram;

/**
 * Created by liushuming on 2017/12/30.
 */
import java.io.Serializable;

/**
 * 410爱安全数据协议介绍
 */
public class GPSMsgGram implements Serializable
{

    private static final long serialVersionUID = 1L;

    //gps 消息体
    private byte[] message;
    //终端ID
    private String terminalId;
    //时间
    private String time;
    //速度(单位：km/h)
    private float speed;
    //卫星个数
    private Integer satelliteNum;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Integer getSatelliteNum() {
        return satelliteNum;
    }

    public void setSatelliteNum(Integer satelliteNum) {
        this.satelliteNum = satelliteNum;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("GPSMsgGram:{"+"terminalId:"+getTerminalId()+","
                +"time:"+getTime()+","
                +"speed:"+getSpeed()+","
                +"satelliteNum:"+getSatelliteNum()+"}");
        return sb.toString();
    }
}

