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

import com.pnu.spring.smartfactory.DAO.InspectDAO;
import com.pnu.spring.smartfactory.DAO.RepairDAO;
import com.pnu.spring.smartfactory.Service.InspectService;
import com.pnu.spring.smartfactory.Service.RepairService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
public class RepairController {
//	-- insRepair
//	-- delRepair
//	-- getRepairList
//	-- getRepairDetail
	private static final Logger logger = LoggerFactory.getLogger(RepairController.class);
	@Resource(name = "com.pnu.spring.smartfactory.Service.RepairServiceImpl") 
	private RepairService repairserviceimpl;
	
	// 설비 수리 등록 
	@RequestMapping(value = "/insrepair", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insRepair(@RequestBody Map<String, Object> param) {
		String repair_no = "";
		String reg_dt = "";
//		String repair_no = (String) param.get("repair_no");
//		String reg_dt = (String) param.get("reg_dt");
		//  #{facility_no}, #{req_no}, #{start_dt}, #{end_dt}, #{status}, 
		// #{case_cd}, #{repair_type_cd}, #{repair_amt},#{cause}, #{repair_details},#{remark}, #{reg_id}
		JSONObject jsonObj = new JSONObject();
		try {
			repairserviceimpl.insRepairService(param);
			jsonObj.put("result", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("result", "fail");
		}
		
		
		return jsonObj;
	}
	
	// 설비 수리 삭제 
	@RequestMapping(value = "/delrepair", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject delRepair(@RequestBody Map<String, Object> param) {
		//String category_id = (String) param.get("category_id");
		String repair_no = (String) param.get("repair_no");
		JSONObject jsonObj = new JSONObject();
		try {
			repairserviceimpl.delRepairService(param);
			jsonObj.put("result", "success");
		}
		catch(Exception e) {
			System.out.println("Error : "+e.toString());
			jsonObj.put("result", "fail");
		}
		return jsonObj;
	}
	
	// 설비 수리 목록 조회
	@RequestMapping(value = "/getrepairlist", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getRepairList() {
		List<RepairDAO> datas = repairserviceimpl.getRepairListService();
		return convListToJsonArrary(datas);
	}
	
	// 설비의 수리 상세 조회
	@RequestMapping(value = "/getrepairdetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getRepairDetail(@RequestBody Map<String, Object> param) {
//		String facility_no = (String) param.get("facility_no");
		List<RepairDAO> datas = repairserviceimpl.getRepairDetailService(param);
		return convListToJsonArrary(datas);
	}
	
	// 설비의 수리 상세 조회
		@RequestMapping(value = "/getrepairdetailbyrepairno", method = RequestMethod.POST)
		@ResponseBody
		public JSONArray getRepairDetailByRepairNo(@RequestBody Map<String, Object> param) {
//			String facility_no = (String) param.get("facility_no");
			List<RepairDAO> datas = repairserviceimpl.getRepairDetailByRepairNoService(param);
			return convListToJsonArrary(datas);
		}
	
	private JSONArray convListToJsonArrary(List<RepairDAO> datas) {
		JSONArray jsonarrary = new JSONArray();
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("repair_no", datas.get(i).getRepair_no());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("req_no", datas.get(i).getReq_no());
			jsonObj.put("start_dt", datas.get(i).getStart_dt());
			jsonObj.put("end_dt", datas.get(i).getEnd_dt());
			jsonObj.put("status", datas.get(i).getStatus());
			jsonObj.put("cause", datas.get(i).getCause());
			jsonObj.put("repair_type", datas.get(i).getRepair_type());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
}
