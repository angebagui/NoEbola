/**
 * @author angebagui
 * email:angebagui@gmail.com
 * tel:+225 02 04 25 68 / +225 77 46 33 12
 */
package com.angbeny.twittersearchapi;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
/* Result on
 * https://dev.twitter.com/docs/api/1.1/get/search/tweets
 */
public class Result {
	
	@SerializedName("statuses")
	private ArrayList<Tweet> statuses;
	
	@SerializedName("search_metadata")
	private SearchMetadata search_metadata;

	public ArrayList<Tweet> getStatuses() {
		return statuses;
	}

	public void setStatuses(ArrayList<Tweet> statuses) {
		this.statuses = statuses;
	}

	public SearchMetadata getSearch_metadata() {
		return search_metadata;
	}

	public void setSearch_metadata(SearchMetadata search_metadata) {
		this.search_metadata = search_metadata;
	}
	
	
}
