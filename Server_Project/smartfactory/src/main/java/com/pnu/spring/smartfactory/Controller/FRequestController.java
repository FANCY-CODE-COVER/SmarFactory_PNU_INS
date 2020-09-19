package com.pnu.spring.smartfactory.Controller;

import java.util.HashMap;
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
import util.CustomLogger;

@Controller
public class FRequestController {
	private static final Logger logger = LoggerFactory.getLogger(FacilityController.class);
	@Resource(name = "com.pnu.spring.smartfactory.Service.FRequestServiceImpl") 
	private FRequestService frequestServiceImpl;
	
	// 설비 요청 등록 (점검 or 수리)
	@RequestMapping(value = "/frequest", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insFRequest(@RequestBody Map<String, Object> param) {
//		String req_no = (String) param.get("req_no");
//		String req_dt = (String) param.get("req_dt");
		// #{req_user_id}, #{facility_no}, #{status},  #{req_details}, #{remark}, #{reg_id}
		CustomLogger.printLog(this, "INFO", "설비 요청 등록");
		JSONObject jsonObj = new JSONObject();
		try {
			frequestServiceImpl.insFRequestSerivce(param);
			jsonObj.put("result", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("result", "fail");
		}
		
		return jsonObj;
	}
	
	// 설비 요청 삭제 (점검 or 수리)
	@RequestMapping(value = "/frequest", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delFRequest(String reqno) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("req_no", reqno );
		CustomLogger.printLog(this, "INFO", "설비 요청 삭제");
		JSONObject jsonObj = new JSONObject();
		try {
			frequestServiceImpl.delFRequestSerivce(param);
			jsonObj.put("result", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("result", "fail");
		}
		return jsonObj;
	}
	
	// 설비 요청 목록 조회 (점검 or 수리)
	@RequestMapping(value = "/frequests", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getFRequestList() {
		CustomLogger.printLog(this, "INFO", "요청 목록 조회");
		List<FRequestDAO> datas = frequestServiceImpl.getFRequestListSerivce();
		return convListtoJSONArray(datas);
	}
	
	// 설비의 요청 상세 조회 (점검 or 수리)
	@RequestMapping(value = "/frequestdetailf", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getFRequestDetail(String facilityno) {
		CustomLogger.printLog(this, "INFO", "설비 번호에 따른 요청 상세 조회");
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("facility_no", facilityno );
		List<FRequestDAO> datas = frequestServiceImpl.getFRequestDetailSerivce(param);
		return convListtoJSONArray(datas);
	}
	
	// 설비의 요청 상세 조회 요청번호 기분 (점검 or 수리)
	@RequestMapping(value = "/frequestdetailr", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getFRequestDetailByReqNo(String reqno) {
		CustomLogger.printLog(this, "INFO", "요청 번호에 따른 요청 상세 조회");
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("req_no", reqno );
		List<FRequestDAO> datas = frequestServiceImpl.getFRequestDetailByReqNoService(param);
		return convListtoJSONArray(datas);
	}
	
	
	private JSONArray convListtoJSONArray(List<FRequestDAO> datas) {
		JSONArray jsonarrary = new JSONArray();
		CustomLogger.printLogCount(this, "INFO", "데이터 갯수", datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("req_no", datas.get(i).getReq_no());
			jsonObj.put("user_nm", datas.get(i).getUser_nm());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("insp_rst_no", datas.get(i).getInsp_rst_no());
			jsonObj.put("status", datas.get(i).getStatus());
			jsonObj.put("employee_nm", datas.get(i).getEmployee_nm());
			jsonObj.put("req_details", datas.get(i).getReq_details());
			jsonObj.put("remark", datas.get(i).getRemark());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
	
}
