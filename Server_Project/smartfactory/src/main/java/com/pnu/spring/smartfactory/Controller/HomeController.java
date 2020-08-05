package com.pnu.spring.smartfactory.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.pnu.spring.smartfactory.DAO.*;
import com.pnu.spring.smartfactory.DAO.PopitDAO;
import com.pnu.spring.smartfactory.Mapper.LoginMapper;
import com.pnu.spring.smartfactory.Mapper.PopitMapper;
import com.pnu.spring.smartfactory.Service.DataService;
import com.pnu.spring.smartfactory.Service.LoginService;
import com.pnu.spring.smartfactory.Service.PopitService;
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Resource(name="com.pnu.spring.smartfactory.Service.PopitServiceImpl") // 해당 서비스가 리소스임을 표시합니다.
	private PopitService popitServiceimpl;
	@Resource(name="com.pnu.spring.smartfactory.Service.LoginServiceImpl") // 해당 서비스가 리소스임을 표시합니다.
	private LoginService loginServicimpl;
	@Resource(name="com.pnu.spring.smartfactory.Service.DataServiceImpl") // 해당 서비스가 리소스임을 표시합니다.
	private DataService dataServicimpl;
	
	@Autowired
	Environment env;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
               // XML -> Mapper(DAO) -> Service -> ServiceImpl -> Controller에 해당 함수 실행  
		
		List<PopitDAO> popitmapper = popitServiceimpl.selectlistService();
		// View에서 어떤 이름으로 값을 가져와서 뿌릴 건지 정하는 것입니다.
                // 여기서는 popitMapper 객체를 popitlist로 JSP(View)에 표시할 것입니다.
		
		model.addAttribute("popitlist",popitmapper);
		return "home";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> doLogin(@RequestBody Map<String, Object> param)  {
		 //loginServicimpl.tryloginService( param.get("user_id"), param.get("password"));
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        map.put("user_id", param.get("user_id"));
        map.put("password", param.get("password"));
        return map;
    }
	
	@RequestMapping(value = "/message", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public HashMap<String, Object> sendMEssage() throws IOException  {
        
		String url="https://kauth.kakao.com/oauth/authorize";
		
		URL obj =new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
		con.addRequestProperty("client_id", env.getProperty("kakao.key.rest"));
		con.addRequestProperty("redirect_uri", env.getProperty("kakao.redirect.uri"));
		con.addRequestProperty("response_type", "code");
		String temp =con.getResponseMessage();
		BufferedReader in = new BufferedReader (new InputStreamReader(con.getInputStream()));
		String inputLine;
		String resultXmlText="";
		while ((inputLine = in.readLine() ) != null) {
			resultXmlText += inputLine;
		}
		in.close();
		con.disconnect();
		
				
				
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("aaa",   temp);
        map.put("ccc",   resultXmlText);
        return map;
    }
	
	@RequestMapping(value = "/getdatas", method = {RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> getDatas(@RequestBody Map<String, Object> param)  {
        
		String category_id = (String) param.get("category_id");
		List<DataDAO> datas = dataServicimpl.getDatasService(category_id);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("size", datas.size());
		for(int i=0;i < datas.size(); ++i)
		{
			
			HashMap<String, Object> submap = new HashMap<String, Object>();
			
			submap.put("code", datas.get(i).getCode());
			submap.put("code_nm", datas.get(i).getCode_nm());
			submap.put("category_id", datas.get(i).getCategory_id());
			submap.put("description", datas.get(i).getDescription());
			
			map.put(datas.get(i).getCode(), submap);
			
		}
		
		
        return map;
    }
	
}
