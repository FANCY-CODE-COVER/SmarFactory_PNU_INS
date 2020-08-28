package com.pnu.spring.smartfactory.Service;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.FacilityDAO;
import com.pnu.spring.smartfactory.DAO.PlaceDAO;

public interface FacilityService {
	public List<PlaceDAO> getPlaceListService();
	public List<FacilityDAO> getFacilityListPerPlaceService(String place_cd);
	public List<FacilityDAO> getFacilityDetailService(String facility_cd);
}
