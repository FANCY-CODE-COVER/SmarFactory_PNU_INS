package com.pnu.spring.smartfactory.Mapper;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.DataDAO;
import com.pnu.spring.smartfactory.DAO.FacilityDAO;
import com.pnu.spring.smartfactory.DAO.PlaceDAO;

public interface FacilityMapper {
	public List<PlaceDAO> getPlaceList();
	public List<FacilityDAO> getFacilityListPerPlace(Map<String, Object> param);
	public List<FacilityDAO> getFacilityDetail(Map<String, Object> param);
}



