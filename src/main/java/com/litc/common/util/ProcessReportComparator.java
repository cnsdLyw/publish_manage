package com.litc.common.util;

import java.util.Comparator;

import com.litc.common.help.model.ProcessModel;

public class ProcessReportComparator implements Comparator<ProcessModel>{

	@Override
	public int compare(ProcessModel pm1, ProcessModel pm2) {
		if(pm1.getCompleteNum()==null){
			pm1.setCompleteNum(0);
		}
		
		if(pm2.getCompleteNum()==null){
			pm2.setCompleteNum(0);
		}
		if(pm1.getCompleteNum()>pm2.getCompleteNum()){
			return -1;
		}else if(pm1.getCompleteNum()<pm2.getCompleteNum()){
			return 1;
		}else{
			return 0;
		}
	}
    
}