package com.sf.fvp;

import com.sf.fvp.util.ProtostuffRuntimeUtil1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvertUtil
{
    public static <T extends IDto> byte[] toByte(T data)
    {
        return ProtostuffRuntimeUtil1.serialize(data);
    }

    public static <T extends IDto> List<byte[]> toListByte(List<T> datas)
    {
        return ProtostuffRuntimeUtil1.serialize(datas);
    }

    public static <T extends IDto> T fromByte(Class<T> clazz, byte[] data)
    {
        return (T) ProtostuffRuntimeUtil1.deSerialize(clazz, data);
    }

    public static <T extends IDto> List<T> fromByteList(Class<T> clazz, List<byte[]> datas)
    {
        if (datas == null) {
            return Collections.emptyList();
        }
        List<T> results = new ArrayList(datas.size());
        for (byte[] b : datas) {
            if (b != null) {
                results.add(ProtostuffRuntimeUtil1.deSerialize(clazz, b));
            }
        }
        return results;
    }
}
