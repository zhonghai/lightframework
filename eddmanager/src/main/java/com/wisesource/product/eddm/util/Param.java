package com.wisesource.product.eddm.util;

import java.util.Map;

public class Param {
	public static String oneVal(Map param,String attrNm){
    	if(param == null) return "";
    	String[] s = null;
    	String	sRtn = "";
    	if(param.get(attrNm) != null){
    		s = (String[]) param.get(attrNm);
    		if(s.length > 0 ){
    			sRtn = s[0];
    		}
    	}	   	
    	return sRtn;
    }
}
