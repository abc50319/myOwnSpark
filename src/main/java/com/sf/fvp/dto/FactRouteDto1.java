package com.sf.fvp.dto;

import com.dyuproject.protostuff.Tag;
import java.util.Date;

public class FactRouteDto1
        extends BarRecordBaseDto
        implements IStateableDto, Comparable<FactRouteDto>
{
    @Tag(50)
    private int dataStatus = 1;
    @Tag(51)
    private int status;
    @Tag(52)
    private String mainWaybillNo;
    @Tag(53)
    private long ordinal;
    @Tag(54)
    private Date createTm;

    public String getMainWaybillNo()
    {
        return this.mainWaybillNo;
    }

    public void setMainWaybillNo(String mainWaybillNo)
    {
        this.mainWaybillNo = mainWaybillNo;
    }

    public int getDataStatus()
    {
        return this.dataStatus;
    }

    public void setDataStatus(int status)
    {
        this.dataStatus = status;
    }

    public int getStatus()
    {
        return this.status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public long getOrdinal()
    {
        return this.ordinal;
    }

    public void setOrdinal(long ordinal)
    {
        this.ordinal = ordinal;
    }

    public String toString()
    {
        return getKey() + ',' + getTime(getCreateTm()) + ',' + this.mainWaybillNo + ',' + getWaybillNo() + ',' + getZoneCode() + ',' + getOpCode() + ',' + getContnrCode() + ',' + getOpAttachInfo() + ',' + getObjTypeCode() + ',' + getBarScanTm() + ',' + this.dataStatus + ',' + this.status + ',' + this.ordinal + ',' + getCreateTm() + '\n';
    }

    public String basictoString() {
        return "BarRecordBaseDto [barRecordId=" + getBarRecordId() + ", waybillNo=" + getWaybillNo() +
                ", zoneCode=" + getWaybillNo() + ", opCode=" + getOpCode() + ", destzonecode=" + getDestZoneCode() + "+]";

    }

    private Object getTime(Date d)
    {
        return d == null ? "" : Long.valueOf(d.getTime());
    }

    public String getKey()
    {
        return 68 + this.mainWaybillNo + '_' + getWaybillNo() + '_' + getContnrCode() + '_' + getZoneCode() + '_' + getUUID(getBarScanTm()) + '_' + getOpCode();
    }

    private String getUUID(Date d)
    {
        return d == null ? "" : String.valueOf(d.getTime());
    }

    public Date getCreateTm()
    {
        return this.createTm;
    }

    public void setCreateTm(Date createTm)
    {
        this.createTm = createTm;
    }

    public boolean isOriginalRecord()
    {
        return (isOriginalFlag()) || (this.status == 4);
    }

    public int compareTo(FactRouteDto i)
    {
        return getOrdinal() == i.getOrdinal() ? 0 : getOrdinal() < i.getOrdinal() ? -1 : 1;
    }
}
