package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

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
	public List<FacilityDAO> getFacilityListPerPlaceService( Map<String, Object> param){
		return facilityMapper.getFacilityListPerPlace(param);
	}
	public List<FacilityDAO> getFacilityDetailService(Map<String, Object> param){
		return facilityMapper.getFacilityDetail(param);
	}
}
