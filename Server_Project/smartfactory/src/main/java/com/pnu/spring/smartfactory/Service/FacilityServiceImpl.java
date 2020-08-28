package com.pnu.spring.smartfactory.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pnu.spring.smartfactory.DAO.DataDAO;
import com.pnu.spring.smartfactory.DAO.FacilityDAO;
import com.pnu.spring.smartfactory.DAO.PlaceDAO;
import com.pnu.spring.smartfactory.Mapper.DataMapper;
import com.pnu.spring.smartfactory.Mapper.FacilityMapper;

@Service("com.pnu.spring.smartfactory.Service.FacilityServiceImpl")
public class FacilityServiceImpl implements FacilityService{
	@Autowired  
	private FacilityMapper facilityMapper;
	public List<PlaceDAO> getPlaceListService() {
		return facilityMapper.getPlaceList();
	}
	public List<FacilityDAO> getFacilityListPerPlaceService(String place_cd){
		return facilityMapper.getFacilityListPerPlace(place_cd);
	}
	public List<FacilityDAO> getFacilityDetailService(String facility_cd){
		return facilityMapper.getFacilityDetail(facility_cd);
	}
}
