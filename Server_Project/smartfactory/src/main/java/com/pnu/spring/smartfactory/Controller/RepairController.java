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

import com.pnu.spring.smartfactory.DAO.InspectDAO;
import com.pnu.spring.smartfactory.DAO.RepairDAO;
import com.pnu.spring.smartfactory.Service.InspectService;
import com.pnu.spring.smartfactory.Service.RepairService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import util.CustomLogger;

@Controller
public class RepairController {
	private static final Logger logger = LoggerFactory.getLogger(RepairController.class);
	@Resource(name = "com.pnu.spring.smartfactory.Service.RepairServiceImpl") 
	private RepairService repairserviceimpl;
	
	// ���� ���� ��� 
	@RequestMapping(value = "/repair", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insRepair(@RequestBody Map<String, Object> param) {
		CustomLogger.printLog(this, "INFO", "���� ���");
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
	
	// ���� ���� ���� 
	@RequestMapping(value = "/repair", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delRepair(String repairno) {
		CustomLogger.printLog(this, "INFO", "���� ����");
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("repair_no", repairno );
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
	
	// ���� ���� ��� ��ȸ
	@RequestMapping(value = "/repairs", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getRepairList() {
		CustomLogger.printLog(this, "INFO", "���� ��� ��ȸ");
		List<RepairDAO> datas = repairserviceimpl.getRepairListService();
		return convListToJsonArrary(datas);
	}
	
	// ������ ���� �� ��ȸ
	@RequestMapping(value = "/repairdetailf", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getRepairDetail(String facilityno) {
		CustomLogger.printLog(this, "INFO", "���� ��ȣ�� ���� ���� �� ��ȸ");
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("facility_no", facilityno );
		List<RepairDAO> datas = repairserviceimpl.getRepairDetailService(param);
		return convListToJsonArrary(datas);
	}
	
	// ������ ���� �� ��ȸ
		@RequestMapping(value = "/repairdetailr", method = RequestMethod.GET)
		@ResponseBody
		public JSONArray getRepairDetailByRepairNo(String repairno) {
			CustomLogger.printLog(this, "INFO", "���� ��ȣ�� ���� ���� �� ��ȸ");
			Map<String, Object> param= new HashMap<String, Object>();
			param.put("repair_no", repairno );
			List<RepairDAO> datas = repairserviceimpl.getRepairDetailByRepairNoService(param);
			return convListToJsonArrary(datas);
		}
	
	//����Ʈ�� JSON �迭�� ��ȯ�ϴ� �޼ҵ�
	private JSONArray convListToJsonArrary(List<RepairDAO> datas) {
		JSONArray jsonarrary = new JSONArray();
		CustomLogger.printLogCount(this, "INFO", "������ ����", datas.size());
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
