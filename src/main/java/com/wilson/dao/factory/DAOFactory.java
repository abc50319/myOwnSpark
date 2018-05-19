package com.wilson.dao.factory;


import com.wilson.dao.GpsMessageInsertDAO;
import com.wilson.dao.GpsMessageScanDAO;
import com.wilson.dao.impl.GpsMessageInsertDAOImpl;
import com.wilson.dao.impl.GpsMessageScanDAOImpl;

/**
 * DAO工厂类
 * @author liushuming
 *
 */
public class DAOFactory {

    public static GpsMessageInsertDAO gpsMessageInsertDAO(){ return  new GpsMessageInsertDAOImpl(); }

    public static GpsMessageScanDAO gpsMessageScanDAO(){ return  new GpsMessageScanDAOImpl(); }



}
