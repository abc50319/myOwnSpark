package com.wilson.util;

import java.text.ParseException;
import java.util.*;

/**
 * 日期时间工具类
 * @author Administrator
 *
 */
public class GPSBusinessUtils {


	/**
	 * function 计算离线配对的计算时长
	 * 比如输入两个时间戳,1521520569000(2018/3/20 12:36:9),1521524169000(2018/3/20 13:36:9)
	 * 返回十分钟级别的数据list<20180320123,20180320124,20180320125,20180320120,20180320121,20180320122,20180320123>
	 */
	public static List getSeconds(String s1, String s2) throws ParseException {
		List<Integer> list = new ArrayList<Integer>();

		//首先要确保是不同时段的数据
		if(DateUtils.TimeStamp2TenMinuteMillisecond(s1) == DateUtils.TimeStamp2TenMinuteMillisecond(s2)){
			list.add(DateUtils.getSeconds(s1,s2));
		}else{

		}



		if(DateUtils.TimeStamp2TenMinuteMillisecond(s1) != DateUtils.TimeStamp2TenMinuteMillisecond(s2)){
			String closeTime = DateUtils.TimeStamp2TenMinuteMillisecond(s1);
			String connectTime = DateUtils.TimeStamp2TenMinuteMillisecond(s2);
			if(Long.parseLong(s2)-Long.parseLong(s1)>=0){
				closeTime=s2;
				connectTime=s1;
			}
			//相同年月日时
			if(true){

			}


		}

		return null;
	}

}
