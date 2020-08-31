package com.pnu.spring.smartfactory.Controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pnu.spring.smartfactory.DAO.FRequestDAO;
import com.pnu.spring.smartfactory.DAO.InspectDAO;
import com.pnu.spring.smartfactory.Service.FRequestService;
import com.pnu.spring.smartfactory.Service.InspectService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
public class InspectController {
//	-- insInspect
//	-- delInspect
//	-- getInspectList
//	-- getInspectDetail
	
	private static final Logger logger = LoggerFactory.getLogger(InspectController.class);
	@Resource(name = "com.pnu.spring.smartfactory.Service.InspectServiceImpl") 
	private InspectService inspectserviceimpl;
	
	// 설비 점검 등록 
	@RequestMapping(value = "/insinspect", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insInspect(@RequestBody Map<String, Object> param) {
		String insp_rst_no = "";
		String insp_date = "";
//		String insp_rst_no = (String) param.get("insp_rst_no");
//		String insp_date = (String) param.get("insp_date");
		JSONObject jsonObj = new JSONObject();
		try {
			inspectserviceimpl.insInspectService(param);
			jsonObj.put("message", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("message", "fail");
		}
		
		return jsonObj;
	}
	
	// 설비 점검 삭제
	@RequestMapping(value = "/delinspect", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject delInspect(@RequestBody Map<String, Object> param) {
		//String category_id = (String) param.get("category_id");
		String insp_rst_no = (String) param.get("insp_rst_no");
		JSONObject jsonObj = new JSONObject();
		try {
			inspectserviceimpl.delInspectService(param);
			jsonObj.put("message", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("message", "fail");
		}
		return jsonObj;
	}
	
	// 설비 점검 목록 조회
	@RequestMapping(value = "/getinspectlist", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getInspectList() {
		List<InspectDAO> datas = inspectserviceimpl.getInspectListService();
		return convListtoJSONArray(datas);
	}

	
	
	// 설비의 점검 상세 조회 
	@RequestMapping(value = "/getinspectdetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getInspectDetail(@RequestBody Map<String, Object> param) {
		String facility_no = (String) param.get("facility_no");
		List<InspectDAO> datas = inspectserviceimpl.getInspectDetailService(param);
		return convListtoJSONArray(datas);
	}
	
	private JSONArray convListtoJSONArray(List<InspectDAO> datas) {
		JSONArray jsonarrary = new JSONArray();
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("insp_rst_no", datas.get(i).getInsp_rst_no());
			jsonObj.put("facility_nm", datas.get(i).getFacility_nm());
			jsonObj.put("inspect_nm", datas.get(i).getInspect_nm());
			jsonObj.put("result", datas.get(i).getResult());
			jsonObj.put("user_nm", datas.get(i).getUser_nm());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("emplyee_nm", datas.get(i).getEmplyee_nm());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
}
