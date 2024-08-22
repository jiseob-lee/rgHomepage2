package com.rg.goAccess.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;

public class GoAccessFileFilter implements FilenameFilter {
	
	private String date;
	
	public GoAccessFileFilter(LocalDate date) {
		this.date = date.toString();
	}
	
	@Override
	public boolean accept(File dir, String name) {
		boolean isAccept = false;
		
		if (name.indexOf("access_log." + date) > -1 && name.indexOf("jisblee") > -1) {
			isAccept = true;
		} else {
			isAccept = false;
		}

		return isAccept;
	}
}
