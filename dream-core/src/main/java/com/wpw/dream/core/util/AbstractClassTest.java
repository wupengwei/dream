package com.wpw.dream.core.util;

public abstract class AbstractClassTest {
	
	private static final String a = "0";
	
	private String get() {
		return "";
	}
	
	
	abstract String set();

}

abstract class Test extends AbstractClassTest {

	abstract String set() ;
	
}