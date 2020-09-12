package com.pnu.spring.smartfactory.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import com.pnu.spring.smartfactory.DAO.*;
import com.pnu.spring.smartfactory.Service.DataService;
import com.pnu.spring.smartfactory.Service.LoginService;
import com.pnu.spring.smartfactory.Service.PopitService;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

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

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		  Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
	        String strToAnalyze = "���������� ���̵� ��� ����� ���峵�ٰ� ������";

	        KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

	        System.out.println(analyzeResultList.getPlainText());
	        System.out.println(analyzeResultList.getMorphesByTags("NNP"));
	        List<Token> tokenList = analyzeResultList.getTokenList();
	        for (Token token : tokenList) {
	            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
	        }

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
		//param.get("user_id"), param.get("password")
		List<String> count = loginServicimpl.tryloginService(param);
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println("try login");
		int count_int=0;
		try {
			count_int= Integer.parseInt(count.get(0));
			if(count_int==1) {
				map.put("result", "success");
				System.out.println("login success");
			}
			else
			{
				map.put("result", "fail");
				System.out.println("login fail");
			}
		}
		catch(Exception ex ) {
			System.out.println(ex.toString());
			map.put("result", ex.toString());
		}
		
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


}
