package com.pnu.spring.smartfactory.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.pnu.spring.smartfactory.DAO.LoginDAO;
import com.pnu.spring.smartfactory.DAO.PopitDAO;
import com.pnu.spring.smartfactory.Mapper.LoginMapper;
import com.pnu.spring.smartfactory.Mapper.PopitMapper;
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
        
        
        
        
//        List<LoginDAO> Loginmapper = loginServicimpl.tryloginService(data.getUser_id(), data.getPassword());
//        testJson = "{\"message\":\"" +"Success_"+Loginmapper.get(0).getUser_id()+ "\"}";
//            
//        try {
//            response.getWriter().print(testJson );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }   
    }
	
}
