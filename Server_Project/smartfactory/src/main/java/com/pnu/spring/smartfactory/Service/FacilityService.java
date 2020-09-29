package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.FacilityDAO;
import com.pnu.spring.smartfactory.DAO.PlaceDAO;

public interface FacilityService {
	public List<PlaceDAO> getPlaceListService();
	public List<FacilityDAO> getFacilityListPerPlaceService(Map<String, Object> param);
	public List<FacilityDAO> getFacilityDetailService(Map<String, Object> param);
}
