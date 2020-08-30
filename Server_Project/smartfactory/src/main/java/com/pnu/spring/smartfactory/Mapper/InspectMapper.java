package com.pnu.spring.smartfactory.Mapper;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.FRequestDAO;
import com.pnu.spring.smartfactory.DAO.InspectDAO;

public interface InspectMapper {
	public void insInspect(Map<String, Object> param);
	public void delInspect(Map<String, Object> param);
	public List<InspectDAO> getInspectList();
	public List<InspectDAO> getInspectDetail(Map<String, Object> param);
}
