package com.angbeny.util;

public class NetworkEvent {
	private boolean status;
	
	public NetworkEvent(){
		
	}
	public NetworkEvent(boolean status){
		this.status = status;
	}

	
	public  boolean getStatus(){
		return status;
	}
}
