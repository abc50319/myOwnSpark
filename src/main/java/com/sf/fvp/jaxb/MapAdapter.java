package com.sf.fvp.jaxb;

import com.sf.fvp.dto.query.FactRouteQueryTranslateDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MapAdapter
        extends XmlAdapter<MapAdapter.MapEntity[], Map<String, List<FactRouteQueryTranslateDto>>>
{
    public MapEntity[] marshal(Map<String, List<FactRouteQueryTranslateDto>> map)
            throws Exception
    {
        MapEntity[] list = new MapEntity[map.size()];
        int index = 0;
        for (Map.Entry<String, List<FactRouteQueryTranslateDto>> entry : map.entrySet())
        {
            MapEntity item = new MapEntity();
            item.key = ((String)entry.getKey());
            item.value = ((List)entry.getValue());
            list[(index++)] = item;
        }
        return list;
    }

    public Map<String, List<FactRouteQueryTranslateDto>> unmarshal(MapEntity[] list)
            throws Exception
    {
        Map<String, List<FactRouteQueryTranslateDto>> map = new HashMap();
        for (int i = 0; i < list.length; i++)
        {
            MapEntity item = list[i];
            map.put(item.key, item.value);
        }
        return map;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name="mapEntitydto")
    public static class MapEntity
    {
        String key;
        @XmlElement(name="value")
        List<FactRouteQueryTranslateDto> value;

        public String getKey()
        {
            return this.key;
        }

        public void setKey(String key)
        {
            this.key = key;
        }

        public List<FactRouteQueryTranslateDto> getValue()
        {
            return this.value;
        }

        public void setValue(List<FactRouteQueryTranslateDto> value)
        {
            this.value = value;
        }
    }
}
