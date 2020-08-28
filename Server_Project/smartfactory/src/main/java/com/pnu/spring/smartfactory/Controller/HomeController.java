package com.pnu.spring.smartfactory.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import com.pnu.spring.smartfactory.DAO.*;
import com.pnu.spring.smartfactory.Mapper.LoginMapper;
import com.pnu.spring.smartfactory.Mapper.PopitMapper;
import com.pnu.spring.smartfactory.Service.DataService;
import com.pnu.spring.smartfactory.Service.FacilityService;
import com.pnu.spring.smartfactory.Service.LoginService;
import com.pnu.spring.smartfactory.Service.PopitService;

import constant.ApiValue;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Resource(name = "com.pnu.spring.smartfactory.Service.PopitServiceImpl") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private PopitService popitServiceimpl;
	@Resource(name = "com.pnu.spring.smartfactory.Service.LoginServiceImpl") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private LoginService loginServicimpl;
	@Resource(name = "com.pnu.spring.smartfactory.Service.DataServiceImpl") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private DataService dataServicimpl;
	@Resource(name = "com.pnu.spring.smartfactory.Service.FacilityServiceImpl") 
	private FacilityService facilityServiceimpl;
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		// XML -> Mapper(DAO) -> Service -> ServiceImpl -> Controller�� �ش� �Լ� ����

		//List<PopitDAO> popitmapper = popitServiceimpl.selectlistService();
		// View���� � �̸����� ���� �����ͼ� �Ѹ� ���� ���ϴ� ���Դϴ�.
		// ���⼭�� popitMapper ��ü�� popitlist�� JSP(View)�� ǥ���� ���Դϴ�.

		//model.addAttribute("popitlist", popitmapper);
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> doLogin(@RequestBody Map<String, Object> param) {
		// loginServicimpl.tryloginService( param.get("user_id"),
		// param.get("password"));
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("user_id", param.get("user_id"));
		map.put("password", param.get("password"));
		return map;
	}

	// �ӽ÷� ������ ��û ���ָ� �����ִ� �޼ҵ�
	@RequestMapping(value = "/getdatas", method = { RequestMethod.POST })
	@ResponseBody
	public JSONArray getDatas(@RequestBody Map<String, Object> param) {

		String category_id = (String) param.get("category_id");
		System.out.println("category_id : " + category_id);
		List<DataDAO> datas = dataServicimpl.getDatasService(category_id);
		JSONArray jsonarrary = new JSONArray();
		
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code", datas.get(i).getCode());
			jsonObj.put("code_nm", datas.get(i).getCode_nm());
			jsonObj.put("category_id", datas.get(i).getCategory_id());
			jsonObj.put("description", datas.get(i).getDescription());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
	// īī�� �α��� �Ҷ� ó�� ������ �κ�
	@RequestMapping(value = "/beforelogin", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView memberLoginForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("a");
		String kakaoUrl = KakaoController.getAuthorizationUrl(session, ApiValue.kakao_key_rest, ApiValue.kakao_redirect_uri);
		
		/* ������ ���� URL�� View�� ���� */
		mav.setViewName("redirect:"+kakaoUrl);
		
		// īī�� �α���
		mav.addObject("kakao_url", kakaoUrl);
		return mav;
	}// end memberLoginForm()

	// īī�� �α����ؼ� redirect�ؼ� ������ �κ�
	@RequestMapping(value = "/kakaologin.do",  method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void kakaoLogin(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		System.out.println("code �ߵ���");
		ModelAndView mav = new ModelAndView();
		// ������� node�� �����
		JsonNode node = KakaoController.getAccessToken(code,ApiValue.kakao_key_rest, ApiValue.kakao_redirect_uri);
		// accessToken�� ������� �α����� ��� ������ �������
		JsonNode accessToken = node.get("access_token"); // ������� ����
		System.out.println("getAccessToken : "+accessToken.toString());
		mav.setViewName("redirect:"+"http://www.naver.com");
//		return mav;
	}// end kakaoLogin()
	// �ӽ÷� ������ ��û ���ָ� �����ִ� �޼ҵ�
		@RequestMapping(value = "/sendmessage", method = { RequestMethod.POST })
		@ResponseBody
		public void sendMessage(@RequestBody Map<String, Object> param) {
			String accessToken =  (String) param.get("access_token");
			System.out.println("Send Message to ME");
			JsonNode rInfo = KakaoController.sendMessagetoMe(accessToken);
		
		}
		
		@RequestMapping(value = "/getfriend", method = { RequestMethod.POST })
		@ResponseBody
		public void getFriened(@RequestBody Map<String, Object> param) {
			System.out.println("ģ�� �������� + �޽��� ����");
			String accessToken =  (String) param.get("access_token");
			String receiver =  (String) param.get("receiver");
			String btnname =  (String) param.get("btnname");
			String contents =  (String) param.get("contents");
			
			System.out.println("getfriend");
			System.out.println("btnname "+btnname );
			System.out.println("contents "+contents);
			List<String> uuids = new ArrayList<String>();
			int offset=0;
			int limit=5;
			JsonNode freindList = null;
			do {
				freindList = KakaoController.getFriends(accessToken, Integer.toString(offset), Integer.toString(limit));
				for(int i =0; i<freindList.size();++i)
				{
					String nickname=freindList.get(i).get("profile_nickname").toString();
					nickname=nickname.replace("\"", "");
					
					if (nickname.equals(receiver))
					{
						String uuid=freindList.get(i).get("uuid").toString();
						uuids.add(uuid);
						break;
					}
				}
				if(uuids.size()>=1)
				{
					break;
				}
				offset+=limit;
			}
			while(freindList.size() >= limit);

			
			if (uuids.size()>=1)
			{
				KakaoController.sendMessagetoYou(accessToken, uuids, contents,btnname);
			}
			else
			{
				System.out.println("ģ���� �������� ����");
			}
			System.out.println("ģ�� �������� + �޽��� ��");
		}//end getFriend
		
		@RequestMapping(value = "/tokenavailable", method = { RequestMethod.POST })
		@ResponseBody
		public JSONObject isTokenAvailable(@RequestBody Map<String, Object> param) {
			System.out.println("��ū�� ���� ����.");
			String accessToken =  (String) param.get("access_token");
			List<String> uuids = new ArrayList<String>();
			JsonNode response = KakaoController.isTokenAvailable(accessToken);
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("expiresIn", response.get("expires_in") );
			System.out.println("Expires in : "+response.get("expires_in"));
			System.out.println("��ū�� ���� ��");
			return jsonObj;
		}// end 
		
		@RequestMapping(value = "/getnewtoken", method = { RequestMethod.POST })
		@ResponseBody
		public JSONObject getNewToken(@RequestBody Map<String, Object> param) {
			System.out.println("����ū �߱� ����");
			String refreshToken =  (String) param.get("refresh_token");
			JsonNode tokenmanger = KakaoController.getNewToken(ApiValue.kakao_key_rest, refreshToken);
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("access_token", tokenmanger.get("access_token") );
			jsonObj.put("refresh_token", tokenmanger.get("refresh_token") );
			
			System.out.println("refresh_token_expires_in : "+tokenmanger.get("refresh_token_expires_in"));
			System.out.println("����ū �߱� ��");
			return jsonObj;
		}// end
		
		
}
