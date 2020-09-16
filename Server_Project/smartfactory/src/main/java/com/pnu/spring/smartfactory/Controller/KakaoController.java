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
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnu.spring.smartfactory.Service.DataService;

import constant.ApiValue;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import net.minidev.json.JSONObject;

@Controller
public class KakaoController {
	// 임시로 데이터 요청 해주면 보내주는 메소드
	@RequestMapping(value = "/sendmessagetome", method = { RequestMethod.POST })
	@ResponseBody
	public void sendMessage(@RequestBody Map<String, Object> param) {
		String accessToken = (String) param.get("access_token");
		System.out.println("Send Message to ME");
		JsonNode rInfo = sendMessagetoMe(accessToken);
	}

	@RequestMapping(value = "/sendmessagetofriend", method = { RequestMethod.POST })
	@ResponseBody
	public void sendMessageToFriend(@RequestBody Map<String, Object> param) {
		System.out.println("친구 가져오기, 메시지 전송 시작");
		String accessToken = (String) param.get("access_token");
		String receiver = (String) param.get("receiver");
		String btnname = (String) param.get("btnname");
		String contents = (String) param.get("contents");

		System.out.println("getfriend");
		System.out.println("btnname " + btnname);
		System.out.println("contents " + contents);
		List<String> uuids = new ArrayList<String>();
		int offset = 0;
		int limit = 5;
		String a = "";

		JsonNode freindList = null;
		do {
			freindList = getFriends(accessToken, Integer.toString(offset), Integer.toString(limit));
			for (int i = 0; i < freindList.size(); ++i) {
				String nickname = freindList.get(i).get("profile_nickname").toString();
				nickname = nickname.replace("\"", "");

				if (nickname.equals(receiver)) {
					String uuid = freindList.get(i).get("uuid").toString();
					uuids.add(uuid);
					break;
				}
			}
			if (uuids.size() >= 1) {
				break;
			}
			offset += limit;
		} while (freindList.size() >= limit);

		if (uuids.size() >= 1) {
			KakaoController.sendMessagetoYou(accessToken, uuids, contents, btnname);
		} else {
			System.out.println("친구가 존재하지 않음");
		}
		System.out.println("친구 가져오기, 메시지 전송 끝");
	}// end getFriend

	@RequestMapping(value = "/sendmessagebyvoice", method = { RequestMethod.POST })
	@ResponseBody
	public void sendMessageByVoice(@RequestBody Map<String, Object> param) {
		System.out.println("목소리로 메시지 보내기 시작");
		String accessToken = (String) param.get("access_token");
		String rawstring = (String) param.get("rawstring");

		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);		
		KomoranResult analyzeResultList = komoran.analyze(rawstring);

		List<String> strList = analyzeResultList.getMorphesByTags("NNP");
		String receiver = "";
		String contents = "";
		 
		for (String str1 : strList) {
			receiver += str1;
		}
		List<Token> tokenList = analyzeResultList.getTokenList();
		boolean NNPflag = false;
		for (Token token : tokenList) {
			for (String str2 : strList) {
				if (str2.equals(token.getMorph())) {
					NNPflag = true;
				}
			}
			if (NNPflag) {
				if (token.getPos().equals("JKB")) {
					contents = rawstring.substring(token.getEndIndex(), rawstring.length());
				}
			}
		}
		String btnname = "";
		System.out.println("getfriend");
		System.out.println("receiver " + receiver);
		System.out.println("btnname " + btnname);
		System.out.println("contents " + contents);
		List<String> uuids = new ArrayList<String>();
		int offset = 0;
		int limit = 5;
		String a = "";

		JsonNode freindList = null;
		do {
			freindList = getFriends(accessToken, Integer.toString(offset), Integer.toString(limit));
			if (freindList != null) {
				for (int i = 0; i < freindList.size(); ++i) {
					String nickname = freindList.get(i).get("profile_nickname").toString();
					nickname = nickname.replace("\"", "");

					if (nickname.equals(receiver)) {
						String uuid = freindList.get(i).get("uuid").toString();
						uuids.add(uuid);
						System.out.println("uuid :"+uuid);
						break;
					}
				}
				if (uuids.size() >= 1) {
					break;
				}
				offset += limit;
			}
		} while (freindList != null && freindList.size() >= limit);

		if (uuids.size() >= 1) {
			KakaoController.sendMessagetoYou(accessToken, uuids, contents, btnname);
		} else {
			System.out.println("친구가 존재하지 않음");
		}
		System.out.println("목소리로 메시지 보내기  끝");
	}// end getFriend

	@RequestMapping(value = "/tokenavailable", method = { RequestMethod.GET })
	@ResponseBody
	public JSONObject isTokenAvailableRouting(String accesstoken) {
		System.out.println("토큰을 점검 시작.");
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("access_token", accesstoken );
		String accessToken = (String) param.get("access_token");
		JsonNode response = KakaoController.isTokenAvailable(accesstoken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("expiresIn", response.get("expires_in"));
		System.out.println("Expires in : " + response.get("expires_in"));
		System.out.println("토큰을 점검 끝");
		return jsonObj;
	}// end

	@RequestMapping(value = "/getnewtoken", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject getNewToken(@RequestBody Map<String, Object> param) {
		System.out.println("새토큰 발급 시작");
		String refreshToken = (String) param.get("refresh_token");
		JsonNode tokenmanger = KakaoController.getNewToken(ApiValue.kakao_key_rest, refreshToken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("access_token", tokenmanger.get("access_token"));
		jsonObj.put("refresh_token", tokenmanger.get("refresh_token"));
		System.out.println(" access_token: " + tokenmanger.get("access_token"));
		System.out.println("refresh_token_expires_in : " + tokenmanger.get("refresh_token_expires_in"));
		System.out.println("새토큰 발급 끝");
		return jsonObj;
	}// end

	@ResponseBody
	synchronized public static String getAuthorizationUrl(HttpSession session, String rest_api,
			String redirection_uri) {
		String kakaoUrl = "";
		kakaoUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + rest_api + "&redirect_uri=" + redirection_uri
				+ "&response_type=code";
		return kakaoUrl;
	}// end 인증코드 메소드

	// 카카오 인증코드로 액세스 토큰 받아오는 부분
	@ResponseBody
	public static JsonNode getAccessToken(String autorize_code, String rest_api, String redirection_uri) {
		final String RequestUrl = "https://kauth.kakao.com/oauth/token";
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id", rest_api)); // REST API KEY
		postParams.add(new BasicNameValuePair("redirect_uri", redirection_uri)); // 리다이렉트 // URI
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
	public static JsonNode getFriends(String accessToken, String offset, String limit) {

		final String RequestUrl = "https://kapi.kakao.com/v1/api/talk/friends?offset=" + offset + "&limit=" + limit
				+ "&order=asc";
		// final String RequestUrl =
		// "https://kapi.kakao.com/v1/api/talk/friends?offset=5&limit=5&order=asc";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet get = new HttpGet(RequestUrl); // add header
		get.addHeader("Authorization", "Bearer " + accessToken);
		
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();

		JsonNode returnNode = null;
		try {

			final HttpResponse response = client.execute(get); // JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
			returnNode = returnNode.get("elements");
			System.out.println("getFriends "+returnNode.toString() );
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


	@ResponseBody
	public static JsonNode sendMessagetoYou(String accessToken, List<String> uuids
			, String contents, String btnname) {
		final String RequestUrl = "https://kapi.kakao.com/v1/api/talk/friends/message/default/send";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl); // add header
		post.addHeader("Authorization", "Bearer " + accessToken);

		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		String uuids_to_string = "[";
		for (int i = 0; i < uuids.size(); ++i) {
			if (i == uuids.size() - 1) {
				uuids_to_string += uuids.get(i);
			} else {
				uuids_to_string += "\"" + uuids.get(i) + ",";
			}
		}
		uuids_to_string += "]";
		postParams.add(new BasicNameValuePair("receiver_uuids", uuids_to_string));
		String subParams = "{" + "\"object_type\": \"text\"," + "\"text\": \"" + contents + "\"," + "\"link\": {"
				+ "	\"web_url\": \"https://developers.kakao.com\","
				+ "\"mobile_web_url\": \"https://developers.kakao.com\" " + "	}," + "	\"button_title\": \"" + btnname
				+ "\"" + "}";

		postParams.add(new BasicNameValuePair("template_object", subParams));
		System.out.println("Send Message to You");

		JsonNode returnNode = null;
		UrlEncodedFormEntity form;
		try {
			form = new UrlEncodedFormEntity(postParams, "utf-8");
			post.setEntity(form);
			final HttpResponse response = client.execute(post); // JSON 형태 반환값 처리
			System.out.println(response.toString());
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

	// 액세스토큰으로 나에게 메시지 보내는 메소드
	@ResponseBody
	public static JsonNode sendMessagetoMe(String accessToken) {

		final String RequestUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl); // add header
		post.addHeader("Authorization", "Bearer " + accessToken);

		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();

		String subParams = "{" + "\"object_type\": \"text\"," + "\"text\": \"This is English\"," + "\"link\": {"
				+ "	\"web_url\": \"https://developers.kakao.com\","
				+ "\"mobile_web_url\": \"https://developers.kakao.com\" " + "	},"
				+ "	\"button_title\": \"This is Button\"" + "}";

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

	@ResponseBody
	public static JsonNode isTokenAvailable(String accessToken) {
		final String RequestUrl = "https://kapi.kakao.com/v1/user/access_token_info";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet get = new HttpGet(RequestUrl); // add header
		get.addHeader("Authorization", "Bearer " + accessToken);
		JsonNode returnNode = null;
		try {
			final HttpResponse response = client.execute(get); // JSON 형태 반환값 처리
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

	@ResponseBody
	public static JsonNode getNewToken(String rest_api_key, String refreshToken) {
		final String RequestUrl = "https://kauth.kakao.com/oauth/token";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl); // add header
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();

		postParams.add(new BasicNameValuePair("grant_type", "refresh_token"));
		postParams.add(new BasicNameValuePair("client_id", rest_api_key));
		postParams.add(new BasicNameValuePair("refresh_token", refreshToken));
		System.out.println("refresh_token "+refreshToken);
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
