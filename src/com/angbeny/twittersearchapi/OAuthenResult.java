/**
 * @author angebagui
 * email:angebagui@gmail.com
 * tel:+225 02 04 25 68 / +225 77 46 33 12
 */
package com.angbeny.twittersearchapi;

import com.google.gson.annotations.SerializedName;

public class OAuthenResult {
	@SerializedName("token_type")
	private String token_type;
	
	@SerializedName("access_token")
	private String access_token;

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	
}
