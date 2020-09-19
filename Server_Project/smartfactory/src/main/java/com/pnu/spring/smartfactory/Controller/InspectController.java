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
import com.pnu.spring.smartfactory.DAO.InspectDAO;
import com.pnu.spring.smartfactory.Service.FRequestService;
import com.pnu.spring.smartfactory.Service.InspectService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import util.CustomLogger;

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
	@RequestMapping(value = "/inspect", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insInspect(@RequestBody Map<String, Object> param) {
		String insp_rst_no = (String) param.get("insp_rst_no");
		String insp_date = (String) param.get("insp_date");
		// #{facility_no}, #{req_no}, #{start_dt}, #{end_dt}, #{status}, 
		// #{case_cd}, #{repair_type_cd}, #{repair_amt},#{cause}, #{repair_details},
		// #{remark}, #{reg_id}
		CustomLogger.printLog(this, "INFO", "점검 등록");
		JSONObject jsonObj = new JSONObject();
		try {
			inspectserviceimpl.insInspectService(param);
			jsonObj.put("result", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("result", "fail");
		}
		
		return jsonObj;
	}
	
	// 설비 점검 삭제
	@RequestMapping(value = "/inspect", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delInspect(String insprstno) {
		CustomLogger.printLog(this, "INFO", "점검 삭제");
		//String category_id = (String) param.get("category_id");
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("insp_rst_no", insprstno );
		JSONObject jsonObj = new JSONObject();
		try {
			inspectserviceimpl.delInspectService(param);
			jsonObj.put("result", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("result", "fail");
		}
		return jsonObj;
	}
	
	// 설비 점검 목록 조회
	@RequestMapping(value = "/inspects", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getInspectList() {
		CustomLogger.printLog(this, "INFO", "점검 목록 조회");
		List<InspectDAO> datas = inspectserviceimpl.getInspectListService();
		return convListtoJSONArray(datas);
	}

	
	
	// 설비의 점검 상세 조회 
	@RequestMapping(value = "/inspectdetailf", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getInspectDetail(String facilityno) {
		CustomLogger.printLog(this, "INFO", "설비 번호에 따른 점검 상세 조회");
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("facility_no", facilityno );
		List<InspectDAO> datas = inspectserviceimpl.getInspectDetailService(param);
		return convListtoJSONArray(datas);
	}
	
	// 설비의 점검 상세 조회 
	@RequestMapping(value = "/inspectdetaili", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getInspectDetailByInspRstNo(String insprstno) {
		CustomLogger.printLog(this, "INFO", "점검 결과 번호에 따른 점검 상세 조회");
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("insp_rst_no", insprstno );
		List<InspectDAO> datas = inspectserviceimpl.getInspectDetailByInspRstNoService(param);
		return convListtoJSONArray(datas);
	}

	private JSONArray convListtoJSONArray(List<InspectDAO> datas) {
		JSONArray jsonarrary = new JSONArray();
		CustomLogger.printLogCount(this, "INFO", "데이터 갯수", datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("insp_rst_no", datas.get(i).getInsp_rst_no());
			jsonObj.put("facility_nm", datas.get(i).getFacility_nm());
			jsonObj.put("inspect_nm", datas.get(i).getInspect_nm());
			jsonObj.put("result", datas.get(i).getResult());
			jsonObj.put("user_nm", datas.get(i).getUser_nm());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("emplyee_nm", datas.get(i).getEmplyee_nm());
			jsonObj.put("req_details", datas.get(i).getReq_details());
			jsonObj.put("remark", datas.get(i).getRemark());
			jsonObj.put("start_dt", datas.get(i).getStart_dt());
			jsonObj.put("end_dt", datas.get(i).getEnd_dt());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
}
