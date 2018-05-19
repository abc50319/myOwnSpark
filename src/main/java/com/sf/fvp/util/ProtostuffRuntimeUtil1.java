package com.sf.fvp.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtostuffRuntimeUtil1
{
    private static final Logger logger = LoggerFactory.getLogger(ProtostuffRuntimeUtil1.class);
    private static final int linkedBufferQueueSize = 512;
    private static final int linkedBufferSize = 512;
    private static final Map<String, Schema> schemas = new ConcurrentHashMap();
    private static final BlockingQueue<LinkedBuffer> linkedBufferPoolQueue = new ArrayBlockingQueue(512);

    static
    {
        for (int i = 0; i < 512; i++) {
            try
            {
                linkedBufferPoolQueue.put(LinkedBuffer.allocate(512));
            }
            catch (InterruptedException e)
            {
                logger.error("init linkedBufferQueue error");
            }
        }
    }

    public static <T> byte[] serialize(T data)
    {
        Class clazz = data.getClass();
        Schema schema = getSchema(clazz);
        byte[] protostuff = null;
        LinkedBuffer linkedBuffer = null;
        try
        {
            linkedBuffer = (LinkedBuffer)linkedBufferPoolQueue.take();
            return ProtostuffIOUtil.toByteArray(data, schema, linkedBuffer);
        }
        catch (InterruptedException e)
        {
            logger.error("serialize error, Class=" + clazz, e);
            throw new RuntimeException(e);
        }
        finally
        {
            if (linkedBuffer != null)
            {
                linkedBuffer.clear();
                try
                {
                    linkedBufferPoolQueue.put(linkedBuffer);
                }
                catch (InterruptedException e)
                {
                    logger.error("put linkedBuffer error, Class=" + clazz, e);
                }
            }
        }
    }

    public static <T> List<byte[]> serialize(List<T> datas)
    {
        if ((datas == null) || (datas.isEmpty())) {
            return null;
        }
        List<byte[]> results = new ArrayList(datas.size());
        Class clazz = datas.get(0).getClass();
        Schema schema = getSchema(clazz);
        LinkedBuffer linkedBuffer = null;
        try
        {
            linkedBuffer = (LinkedBuffer)linkedBufferPoolQueue.take();
            for (T data : datas)
            {
                results.add(ProtostuffIOUtil.toByteArray(data, schema, linkedBuffer));

                linkedBuffer.clear();
            }
            return results;
        }
        catch (InterruptedException e)
        {
            logger.error("serialize error, Class=" + clazz, e);
            throw new RuntimeException(e);
        }
        finally
        {
            if (linkedBuffer != null)
            {
                linkedBuffer.clear();
                try
                {
                    linkedBufferPoolQueue.put(linkedBuffer);
                }
                catch (InterruptedException e)
                {
                    logger.error("put linkedBuffer error, Class=" + clazz, e);
                }
            }
        }
    }

    public static <T> T deSerialize(Class<T> clazz, byte[] data)
    {
        if ((clazz == null) || (data == null)) {
            return null;
        }
        Schema schema = getSchema(clazz);
        T t = null;
        try
        {
            t = clazz.newInstance();
            ProtostuffIOUtil.mergeFrom(data, t, schema);
        }
        catch (InstantiationException e)
        {
            logger.error("InstantiationExceptionxx deSerialize error, Class=" + clazz, e);
        }
        catch (IllegalAccessException e)
        {
            logger.error("IllegalAccessExceptionxx deSerialize error, Class=" + clazz, e);
        }
        catch (Exception e)
        {
            logger.error("Exceptionxx deSerialize error, Class=" + clazz, e);
        }
        return t;
    }

    private static <T> Schema<? extends T> getSchema(Class<? extends T> clazz)
    {
        String name = clazz.getName();
        Schema<? extends T> schema = (Schema)schemas.get(name);
        if (schema == null) {
            synchronized (name)
            {
                schema = (Schema)schemas.get(name);
                if (schema == null) {
                    schema = RuntimeSchema.getSchema(clazz);
                }
                schemas.put(name, schema);
            }
        }
        return schema;
    }
}
