package com.pnu.spring.smartfactory.Mapper;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.DataDAO;
import com.pnu.spring.smartfactory.DAO.FacilityDAO;
import com.pnu.spring.smartfactory.DAO.PlaceDAO;

public interface FacilityMapper {
	public List<PlaceDAO> getPlaceList();
	public List<FacilityDAO> getFacilityListPerPlace(String place_cd);
	public List<FacilityDAO> getFacilityDetail(String facility_cd);
}
