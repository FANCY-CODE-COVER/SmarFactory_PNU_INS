package com.pnu.spring.smartfactory.Controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pnu.spring.smartfactory.DAO.FacilityDAO;
import com.pnu.spring.smartfactory.DAO.PlaceDAO;
import com.pnu.spring.smartfactory.Service.FacilityService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
public class FacilityController {
	private static final Logger logger = LoggerFactory.getLogger(FacilityController.class);
	@Resource(name = "com.pnu.spring.smartfactory.Service.FacilityServiceImpl") 
	private FacilityService facilityServiceimpl;
	
	// 설비 동 목록 조회
	@RequestMapping(value = "/getplacelist", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getPlaceList() {
		//String category_id = (String) param.get("category_id");
		List<PlaceDAO> datas = facilityServiceimpl.getPlaceListService();
		JSONArray jsonarrary = new JSONArray();
		
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rowno", datas.get(i).getRowno());
			jsonObj.put("place_cd", datas.get(i).getPlace_cd());
			jsonObj.put("place", datas.get(i).getPlace());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
	
	// 동별 설비 목록 조회
	@RequestMapping(value = "/getfacilitylistperplace", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getFacilityListPerPlace(@RequestBody Map<String, Object> param) {
		String place_cd = (String) param.get("place_cd");
		List<FacilityDAO> datas = facilityServiceimpl.getFacilityListPerPlaceService(place_cd);
		JSONArray jsonarrary = new JSONArray();
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rowno", datas.get(i).getRowno());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("facility_nm", datas.get(i).getFacility_nm());
			jsonObj.put("place_cd", datas.get(i).getPlace_cd());
			jsonObj.put("place", datas.get(i).getPlace());
			jsonObj.put("pline_nm", datas.get(i).getPline_nm());
			jsonObj.put("departmet_nm", datas.get(i).getDepartmet_nm());
			jsonObj.put("employee_nm", datas.get(i).getEmployee_nm());
			jsonObj.put("state", datas.get(i).getState());
			jsonObj.put("facility_type", datas.get(i).getFacility_type());
			jsonObj.put("reg_nm", datas.get(i).getReg_nm());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
	
	//설비 상세 조회
	@RequestMapping(value = "/getfacilitydetail", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getFacilityDetail(@RequestBody Map<String, Object> param) {
		String facility_cd = (String) param.get("facility_cd");
		List<FacilityDAO> datas = facilityServiceimpl.getFacilityDetailService(facility_cd);
		JSONArray jsonarrary = new JSONArray();
		System.out.println("Size:"+datas.size());
		for (int i = 0; i < datas.size(); ++i) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rowno", datas.get(i).getRowno());
			jsonObj.put("facility_no", datas.get(i).getFacility_no());
			jsonObj.put("facility_nm", datas.get(i).getFacility_nm());
			jsonObj.put("place_cd", datas.get(i).getPlace_cd());
			jsonObj.put("place", datas.get(i).getPlace());
			jsonObj.put("pline_nm", datas.get(i).getPline_nm());
			jsonObj.put("departmet_nm", datas.get(i).getDepartmet_nm());
			jsonObj.put("employee_nm", datas.get(i).getEmployee_nm());
			jsonObj.put("state", datas.get(i).getState());
			jsonObj.put("facility_type", datas.get(i).getFacility_type());
			jsonObj.put("reg_nm", datas.get(i).getReg_nm());
			jsonarrary.add(jsonObj);
		}
		return jsonarrary;
	}
}
