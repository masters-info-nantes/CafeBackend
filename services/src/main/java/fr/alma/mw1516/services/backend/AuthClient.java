package fr.alma.mw1516.services.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import fr.alma.mw1516.api.backend.IUser;
import fr.alma.mw1516.services.backend.util.JsonParser;

public class AuthClient {

	private CloseableHttpClient httpClient = HttpClients.createDefault();
	private String host;
	private static String API_KEY = "reQSFGgFSbgc54uyhjg35hgf23vJhg432JNkjH";
	
	public AuthClient(String host) {
		this.host = host;
	}
	
	public IUser token(String token) throws Exception {
		HttpGet tokenReq = new HttpGet(host+"/token");
		
        tokenReq.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        tokenReq.addHeader("Api-Key", API_KEY);
        tokenReq.addHeader("Authorization", token);
        
        // Execution de la requête
        CloseableHttpResponse response = null;
        try {
			response = httpClient.execute(tokenReq);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() != 200) {
        	throw new Exception("Erreur requête token : "+status.getStatusCode()+" "+status.getReasonPhrase());
        }
        JsonParser parser = new JsonParser();
        String json = getContextAsString(response);
        return new User(parser.fetchJsonData(json, "id"),
        				parser.fetchJsonData(json, "first_name"),
        				parser.fetchJsonData(json, "last_name"));
	}
	
    protected static String getContextAsString(HttpResponse response) throws IOException {

        StringWriter writer = new StringWriter();
        InputStream inputStream = response.getEntity().getContent();
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
        } finally {
            inputStream.close();
        }
        return writer.toString();
    }

}
