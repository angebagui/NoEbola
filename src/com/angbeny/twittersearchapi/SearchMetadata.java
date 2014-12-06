/**
 * @author angebagui
 * email:angebagui@gmail.com
 * tel:+225 02 04 25 68 / +225 77 46 33 12
 */
package com.angbeny.twittersearchapi;

import com.google.gson.annotations.SerializedName;

public class SearchMetadata {
	@SerializedName("max_id")
    private long max_id; 

	@SerializedName("since_id")
    private long since_id; 
	
	@SerializedName("refresh_url")
    private String refresh_url;

	@SerializedName("next_results")
	private String next_results;
   
	@SerializedName("count")
	private int count;
	
	@SerializedName("completed_in")
	private float completed_in;
	
	@SerializedName("since_id_str")
	private String since_id_str;
	
	@SerializedName("query")
	private String query;
	
	@SerializedName("max_id_str")
	private String max_id_str;

	public long getMax_id() {
		return max_id;
	}

	public void setMax_id(long max_id) {
		this.max_id = max_id;
	}

	public long getSince_id() {
		return since_id;
	}

	public void setSince_id(long since_id) {
		this.since_id = since_id;
	}

	public String getRefresh_url() {
		return refresh_url;
	}

	public void setRefresh_url(String refresh_url) {
		this.refresh_url = refresh_url;
	}

	public String getNext_results() {
		return next_results;
	}

	public void setNext_results(String next_results) {
		this.next_results = next_results;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getCompleted_in() {
		return completed_in;
	}

	public void setCompleted_in(float completed_in) {
		this.completed_in = completed_in;
	}

	public String getSince_id_str() {
		return since_id_str;
	}

	public void setSince_id_str(String since_id_str) {
		this.since_id_str = since_id_str;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getMax_id_str() {
		return max_id_str;
	}

	public void setMax_id_str(String max_id_str) {
		this.max_id_str = max_id_str;
	}
	
}
