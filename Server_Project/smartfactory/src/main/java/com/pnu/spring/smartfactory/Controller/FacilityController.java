package com.pnu.spring.smartfactory.Controller;

import java.util.HashMap;
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
import util.CustomLogger;

@Controller
public class FacilityController {
	private static final Logger logger = LoggerFactory.getLogger(FacilityController.class);
	@Resource(name = "com.pnu.spring.smartfactory.Service.FacilityServiceImpl") 
	private FacilityService facilityServiceimpl;
	// 설비 동 목록 조회
	@RequestMapping(value = "/places", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getPlaceList() {
		CustomLogger.printLog(this, "INFO", "동 목록 조회");
		List<PlaceDAO> datas = facilityServiceimpl.getPlaceListService();
		JSONArray jsonarrary = new JSONArray();
		CustomLogger.printLogCount(this, "INFO", "데이터 갯수", datas.size());
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
	@RequestMapping(value = "/facilities", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getFacilityListPerPlace(String placecd) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("place_cd", placecd );
		List<FacilityDAO> datas = facilityServiceimpl.getFacilityListPerPlaceService(param);
		CustomLogger.printLog(this, "INFO", "동별 설비 목록 조회 ");
		return convListtoJSONArray(datas);
	}

	//설비 상세 조회
	@RequestMapping(value = "/facilitydetail", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getFacilityDetail(String facilitycd) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("facility_cd", facilitycd );
		List<FacilityDAO> datas = facilityServiceimpl.getFacilityDetailService(param);
		CustomLogger.printLog(this, "INFO", "설비 상세 조회 ");
		return convListtoJSONArray(datas);
	}
	
	private JSONArray convListtoJSONArray(List<FacilityDAO> datas) {
		JSONArray jsonarrary = new JSONArray();
		CustomLogger.printLogCount(this, "INFO", "데이터 갯수", datas.size());
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
