package com.pnu.spring.smartfactory.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnu.spring.smartfactory.Service.DataService;

@Controller
public class KakaoController {

	// 카카오 로그인 처음 부분 
	@ResponseBody
	synchronized public static String getAuthorizationUrl(HttpSession session, String rest_api, String redirection_uri) {
		String kakaoUrl=""; 
		kakaoUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" 
		+ rest_api+"&redirect_uri=" + redirection_uri+ "&response_type=code";
		return kakaoUrl;
	}// end 인증코드 메소드

	// 카카오 인증코드로 액세스 토큰 받아오는 부분
	@ResponseBody
	public static JsonNode getAccessToken(String autorize_code, String rest_api, String redirection_uri) {
		final String RequestUrl = "https://kauth.kakao.com/oauth/token";
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id",  rest_api)); // REST API KEY
		postParams.add(new BasicNameValuePair("redirect_uri", redirection_uri)); // 리다이렉트																												// URI
		postParams.add(new BasicNameValuePair("code", autorize_code)); // 로그인 과정중 얻은 code 값
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);
		JsonNode returnNode = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));
			final HttpResponse response = client.execute(post); // JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// clear resources
		}
		return returnNode;
	}

	// 액세스토큰으로 사용자 정보 받아오는부분
	@ResponseBody
	public static JsonNode getKakaoUserInfo(JsonNode accessToken) {
		final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl); // add header
		post.addHeader("Authorization", "Bearer " + accessToken);
		
		JsonNode returnNode = null;
		try {
			final HttpResponse response = client.execute(post);
			// JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// clear resources
		}
		return returnNode;
	}
	
	// 액세스토큰으로 나에게 메시지 보내는 메소드
	@ResponseBody
	public static JsonNode sendMessagetoMe(JsonNode accessToken) {
		
		final String RequestUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl); // add header
		post.addHeader("Authorization", "Bearer " + accessToken);
				
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		String subParams = 
		"{" + 
		"\"object_type\": \"text\"," + 
		"\"text\": \"This is English\"," + 
		"\"link\": {" + 
		"	\"web_url\": \"https://developers.kakao.com\"," + 
		"\"mobile_web_url\": \"https://developers.kakao.com\" " + 
		"	}," + 
		"	\"button_title\": \"This is Button\"" + 
		"}";
		
		postParams.add(new BasicNameValuePair("template_object", subParams));
		
		
		JsonNode returnNode = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));
			final HttpResponse response = client.execute(post); // JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// clear resources
		}
		return returnNode;
	}

}
