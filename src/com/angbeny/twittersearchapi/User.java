/**
 * @author angebagui
 * email:angebagui@gmail.com
 * tel:+225 02 04 25 68 / +225 77 46 33 12
 */
package com.angbeny.twittersearchapi;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("name")
	private String name;
	
	@SerializedName("profile_image_url")
	private String profile_image_url; 

	@SerializedName("created_at")
    private String created_at;
	
	@SerializedName("id_str")
	private String id_str;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getId_str() {
		return id_str;
	}

	public void setId_str(String i) {
		this.id_str = i;
	}
	
	
}
