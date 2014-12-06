/**
 * @author angebagui
 * email:angebagui@gmail.com
 * tel:+225 02 04 25 68 / +225 77 46 33 12
 */
package com.angbeny.twittersearchapi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

public class TwitterSearchApi {
	private static final String TAG ="TwitterSearchApi";
	private static final String API_KEY = "VnHwS1DJexbE1zROmmepaLTxi";
	private static final String API_SECRET ="mA4ggZ5ekJokFksiYCMoTWbuhamdRuyYshaHZ6inhiYpwLUzNU";
	
	//ENDPOINT
	private static final String ENDPOINT="https://api.twitter.com/1.1/search/tweets.json";
	
	//POST oauth2/token
	private static final String TWITTER_TOKEN_URL ="https://api.twitter.com/oauth2/token";
	//Query
	private static final String QUERY ="#ebola";
	
	/*
	 * Method to get the Tweet by Step on
	 * https://dev.twitter.com/docs/auth/application-only-auth
	 */
	public ArrayList<Tweet> getTweets(){
		ArrayList<Tweet> statuses = new ArrayList<Tweet>();
		Gson  gson = new Gson();
			/*
			 * Step 1: Encode consumer key and secret	
			 */
			try {
				//1--URL encode the consumer key and the consumer secret
				String keyEncoded = URLEncoder.encode(API_KEY, "UTF-8");
				String secretEncoded = URLEncoder.encode(API_SECRET, "UTF-8");
				
				//2--Concatenate the encoded consumer key, a colon character ":", 
				//and the encoded consumer secret into a single string.
				
				String bearerTokenCredentials = keyEncoded+":"+secretEncoded;
				
				//3--Base64 encode the string from the previous step.
				String base64Encoded = Base64.encodeToString(bearerTokenCredentials.getBytes(), Base64.NO_WRAP);
				
				
			/*
			 * Step 2: Obtain a bearer token
			 */
				//The request must be a HTTP POST request.
				HttpPost httpPost = new HttpPost(TWITTER_TOKEN_URL);
				
				//The request must include 
				//an Authorization header with the value of
				//Basic <base64 encoded value from step 1>.
				httpPost.setHeader("Authorization", "Basic "+base64Encoded);
				
				//The request must include a Content-Type header
				//with the value of application/x-www-form-urlencoded;charset=UTF-8.
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
				
				//The body of the request must be grant_type=client_credentials.
				httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
				
				String jsonResult = getUrlResponse(httpPost);

			     
				OAuthenResult  oauth = gson.fromJson(jsonResult, OAuthenResult.class);
				
				
				if(oauth.getToken_type().equals("bearer")){
					
					/*
					 * Step 3: Authenticate API requests with the bearer token
					 */
					     String queryEncoded = URLEncoder.encode(QUERY, "UTF-8");
					     String langEncoded = URLEncoder.encode(getCurrentLanguage(), "UTF-8");
					     String url = Uri.parse(ENDPOINT).buildUpon()
							.appendQueryParameter("q", queryEncoded)
							.appendQueryParameter("lang", langEncoded)
							.build().toString();
					     
					     //GET request
						HttpGet httpGet = new HttpGet(url);
						httpGet.setHeader("Authorization", "Bearer "+oauth.getAccess_token());
						
						//Excecuter la requete et recuperer la reponse
					String resultjson = getUrlResponse(httpGet);
					//Deserialiser en Statuses
				
				  statuses = (gson.fromJson(resultjson, Result.class)).getStatuses();
					
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				Log.e(TAG, "Erreur pendant la recherche de Tweet", e);
			}
			
		return statuses;
	}

	/**
	 * Lancer une requeste de type HttpPost ou HttpGet qui ont pour superclasse
	 * @param HttpRequestBase et va retourner 
	 * @return le contenu en chaine de caractère
	 */
	
	private String getUrlResponse(HttpRequestBase request){
		StringBuilder content = new StringBuilder();
	
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			if(statusCode ==200){
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream in = httpEntity.getContent();
				
				BufferedReader buffer = new BufferedReader(new InputStreamReader(in, "UTF-8"),8);
				
				String line = null;
				while((line = buffer.readLine()) !=null){
					content.append(line);
				}
				in.close();
			}else{
				Log.e(TAG, "Erreur dans getUrlResponse");
			}
		} catch (ClientProtocolException cpe) {
			Log.e(TAG, "Erreur dans getUrlResponse",cpe);;
		} catch (IOException ioe) {
			Log.e(TAG, "Erreur dans getUrlResponse",ioe);
		}
		return content.toString();
	}
	/*
	 * Get the current language in the device
	 */
	private String getCurrentLanguage(){
		return Locale.getDefault().getLanguage();
	}
	/*
	 * Get a byte an Url
	 */
	public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
}
