package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.FRequestDAO;
import com.pnu.spring.smartfactory.DAO.ResponseDAO;

public interface FRequestService {
	public void insFRequestSerivce(Map<String, Object> param);
	public void delFRequestSerivce(Map<String, Object> param);
	public List<FRequestDAO> getFRequestListSerivce();
	public List<FRequestDAO> getFRequestDetailSerivce(Map<String, Object> param);
	public List<FRequestDAO> getFRequestDetailByReqNoService(Map<String, Object> param);
}
