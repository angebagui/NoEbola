/**
 * @author angebagui
 * email:angebagui@gmail.com
 * tel:+225 02 04 25 68 / +225 77 46 33 12
 */
package com.angbeny.twittersearchapi;



import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Tweet implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1722923014846290488L;

	@SerializedName("created_at")
    private String created_at; 
	
	@SerializedName("id_str")
	private String id_str;
	
	@SerializedName("id")
	private long id;
	
	@SerializedName("text")
	private String text;
	
	@SerializedName("user")
	private User user;
	
	@SerializedName("url")
	private String url;
	
	@SerializedName("lang")
	private String lang;
	
	@SerializedName( "profile_image_url_https")
	private  String profile_image_url_https;
	
	private String link;
	
	private String textWithoutLink;
	
	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getId_str() {
		return id_str;
	}

	public void setId_str(String id_str) {
		this.id_str = id_str;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}


	public String getProfile_image_url_https() {
		return profile_image_url_https;
	}

	public void setProfile_image_url_https(String profile_image_url_https) {
		this.profile_image_url_https = profile_image_url_https;
	}

	public String getLink(){
		if(text.contains("http") || text.contains("https")){
			String [] s = text.split(" ");
			for(int i=0; i<s.length; i++){
				if(s[i].startsWith("http")||s[i].startsWith("https")){
					link = s[i];
				}
			}
		}
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTextWithoutLink() {
		if(text.contains("http") || text.contains("https")){
			String [] s = text.split(" ");
			int n = s.length;
			if(s[n-1].startsWith("http")||s[n-1].startsWith("https")){
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<n-1; i++){
					sb.append(s[i]+" ");
				}
				textWithoutLink= sb.toString();
			}else{
				textWithoutLink = getText();
			}
		}else{
			textWithoutLink = getText();
		}
		return textWithoutLink;
	}

	public void setTextWithoutLink(String textWithoutLink) {
		this.textWithoutLink = textWithoutLink;
	}

	@Override
	public String toString() {
		return getText();
	}
	
	
}
