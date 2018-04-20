package com.litc.common.util;

import java.util.Comparator;

import com.litc.common.help.model.StatisticsDemo;

public class StatisticsComparator implements Comparator<StatisticsDemo>{

	@Override
	public int compare(StatisticsDemo o1, StatisticsDemo o2) {
		if(o1.getValue()>o2.getValue()){
			return -1;
		}else if(o1.getValue()<o2.getValue()){
			return 1;
		}else{
			return 0;
		}
	}
    
}