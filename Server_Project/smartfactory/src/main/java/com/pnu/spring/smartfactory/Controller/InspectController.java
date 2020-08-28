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
	
	// 설비 요청 등록 (점검 or 수리)
	@RequestMapping(value = "/insinspect", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insInspect(@RequestBody Map<String, Object> param) {
		String insp_rst_no = "";
		String insp_date = "";
//		String insp_rst_no = (String) param.get("insp_rst_no");
//		String insp_date = (String) param.get("insp_date");
		JSONObject jsonObj = new JSONObject();
		try {
			inspectserviceimpl.insInspectService(insp_rst_no, insp_date);
			jsonObj.put("message", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("message", "fail");
		}
		
		return jsonObj;
	}
	
	// 설비 요청 삭제 (점검 or 수리)
	@RequestMapping(value = "/delinspect", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject delInspect(@RequestBody Map<String, Object> param) {
		//String category_id = (String) param.get("category_id");
		String insp_rst_no = (String) param.get("insp_rst_no");
		JSONObject jsonObj = new JSONObject();
		try {
			inspectserviceimpl.delInspectService(insp_rst_no);
			jsonObj.put("message", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("message", "fail");
		}
		return jsonObj;
	}
	
	// 설비 요청 목록 조회 (점검 or 수리)
	@RequestMapping(value = "/getinspectlist", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getInspectList() {
		List<InspectDAO> datas = inspectserviceimpl.getInspectListService();
		JSONArray jsonarrary = new JSONArray();
		
		//req_no, facility_nm, inspect_nm,	result, user_nm
		//, facility_no, insp_rst_no, emplyee_nm;
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("req_no", datas.get(i).getReq_no());
			jsonObj.put("facility_nm", datas.get(i).getFacility_nm());
			jsonObj.put("inspect_nm", datas.get(i).getInspect_nm());
			jsonObj.put("result", datas.get(i).getResult());
			jsonObj.put("user_nm", datas.get(i).getUser_nm());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("insp_rst_no", datas.get(i).getInsp_rst_no());
			jsonObj.put("emplyee_nm", datas.get(i).getEmplyee_nm());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
	
	// 설비의 요청 상세 조회 (점검 or 수리)
	@RequestMapping(value = "/getinspectdetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getInspectDetail(@RequestBody Map<String, Object> param) {
		String facility_no = (String) param.get("facility_no");
		List<InspectDAO> datas = inspectserviceimpl.getInspectDetailService(facility_no);
		JSONArray jsonarrary = new JSONArray();
		
		//req_no, facility_nm, inspect_nm,	result, user_nm
		//, facility_no, insp_rst_no, emplyee_nm;
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("req_no", datas.get(i).getReq_no());
			jsonObj.put("facility_nm", datas.get(i).getFacility_nm());
			jsonObj.put("inspect_nm", datas.get(i).getInspect_nm());
			jsonObj.put("result", datas.get(i).getResult());
			jsonObj.put("user_nm", datas.get(i).getUser_nm());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("insp_rst_no", datas.get(i).getInsp_rst_no());
			jsonObj.put("emplyee_nm", datas.get(i).getEmplyee_nm());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
}
