package com.angbeny.util;

import com.squareup.otto.Bus;

public final class BusProvider {
	private static final Bus bus = new Bus();
	
	public static Bus getInstance(){
		
		return bus;
	}
	private BusProvider(){
		
	}
}
