package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.InspectDAO;

public interface InspectService {
	public void insInspectService(Map<String, Object> param);
	public void delInspectService(Map<String, Object> param);
	public List<InspectDAO> getInspectListService();
	public List<InspectDAO> getInspectDetailService(Map<String, Object> param);
}
