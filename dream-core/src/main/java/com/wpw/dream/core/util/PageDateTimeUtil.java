package com.wpw.dream.core.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class PageDateTimeUtil {

	/**
	 * 
	 * @Description：
	 * @author: wupengwei
	 * @date: 2016年8月25日 下午5:04:11
	 * @param rangeStr yyyy-mm:年-月;13:近一周;14:近一月；15：近三月 
	 * @return 索引0为startTime;索引1为endTime 不符合的返回null
	 */
	public static List<Long> getTimeRange(String rangeStr) {
		ArrayList<Long> timeRange = new ArrayList<>(2);

		if (StringUtils.isBlank(rangeStr)) {
			return null;
		}
		//近一周
		if ("13".equals(rangeStr)) 
		{
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(getDayBegin());
			c.add(Calendar.DAY_OF_MONTH, 7);
			long endTime = c.getTimeInMillis() - 1;
			timeRange.add(getDayBegin());
			timeRange.add(endTime);
		} 
		//近一月
		else if ("14".equals(rangeStr)) 
		{
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(getDayBegin());
			c.add(Calendar.MONTH, 1);
			long endTime = c.getTimeInMillis() - 1;
			timeRange.add(getDayBegin());
			timeRange.add(endTime);
		}
		
		else if ("15".equals(rangeStr)) 
		{
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(getDayBegin());
			c.add(Calendar.MONTH, 3);
			long endTime = c.getTimeInMillis() - 1;
			timeRange.add(getDayBegin());
			timeRange.add(endTime);
		}

		else
		{
			String[] split = rangeStr.split("-");
			if (split.length != 2) {
				return null;
			}
			
			int year = Integer.parseInt(split[0]);
			int month = Integer.parseInt(split[1]) - 1;
			Calendar c = Calendar.getInstance();
			c.set(year, month, 1, 0, 0, 0);
			c.set(Calendar.MILLISECOND, 0);
			long startTime = c.getTimeInMillis();
			c.set(year, month, c.getActualMaximum(Calendar.DATE), 23, 59, 59);
			long endTime = c.getTimeInMillis();
			timeRange.add(startTime);
			timeRange.add(endTime);
			
			
		}
		return timeRange;
	}

	public static void main(String[] args) {
		List<Long> timeRange = getTimeRange("2016-12");
		System.out.println(timeRange);
	}

	public static Long getDayBegin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 000);
		return cal.getTimeInMillis();
	}
}
