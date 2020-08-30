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
import com.pnu.spring.smartfactory.DAO.FacilityDAO;
import com.pnu.spring.smartfactory.DAO.PlaceDAO;
import com.pnu.spring.smartfactory.Service.FRequestService;
import com.pnu.spring.smartfactory.Service.FacilityService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
public class FRequestController {
	private static final Logger logger = LoggerFactory.getLogger(FacilityController.class);
	@Resource(name = "com.pnu.spring.smartfactory.Service.FRequestServiceImpl") 
	private FRequestService frequestServiceImpl;
	
	// 설비 요청 등록 (점검 or 수리)
	@RequestMapping(value = "/insfrequest", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insFRequest(@RequestBody Map<String, Object> param) {
		String req_no = "";
		String req_dt = "";
//		String req_no = (String) param.get("req_no");
//		String req_dt = (String) param.get("req_dt");
		JSONObject jsonObj = new JSONObject();
		try {
			frequestServiceImpl.insFRequestSerivce(param);
			jsonObj.put("message", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("message", "fail");
		}
		
		return jsonObj;
	}
	
	// 설비 요청 삭제 (점검 or 수리)
	@RequestMapping(value = "/delfrequest", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject delFRequest(@RequestBody Map<String, Object> param) {
		//String category_id = (String) param.get("category_id");
		String req_no = (String) param.get("req_no");
		JSONObject jsonObj = new JSONObject();
		try {
			frequestServiceImpl.delFRequestSerivce(param);
			jsonObj.put("message", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("message", "fail");
		}
		return jsonObj;
	}
	
	// 설비 요청 목록 조회 (점검 or 수리)
	@RequestMapping(value = "/getfrequestlist", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getFRequestList() {
		List<FRequestDAO> datas = frequestServiceImpl.getFRequestListSerivce();
		return convListtoJSONArray(datas);
	}
	
	// 설비의 요청 상세 조회 (점검 or 수리)
	@RequestMapping(value = "/getfrequestdetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getFRequestDetail(@RequestBody Map<String, Object> param) {
		String facility_no = (String) param.get("facility_no");
		List<FRequestDAO> datas = frequestServiceImpl.getFRequestDetailSerivce(param);
		return convListtoJSONArray(datas);
	}
	
	private JSONArray convListtoJSONArray(List<FRequestDAO> datas) {
		JSONArray jsonarrary = new JSONArray();
		
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("req_no", datas.get(i).getReq_no());
			jsonObj.put("user_nm", datas.get(i).getUser_nm());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("insp_rst_no", datas.get(i).getInsp_rst_no());
			jsonObj.put("status", datas.get(i).getStatus());
			jsonObj.put("employee_nm", datas.get(i).getEmployee_nm());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
	
}
