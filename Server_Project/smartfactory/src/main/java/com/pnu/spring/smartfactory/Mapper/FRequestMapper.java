package com.pnu.spring.smartfactory.Mapper;

import com.pnu.spring.smartfactory.DAO.FRequestDAO;
import com.pnu.spring.smartfactory.DAO.ResponseDAO;
import java.util.List;
import java.util.Map;

public interface FRequestMapper {
	public void insFRequest(Map<String, Object> param);
	public void delFRequest(Map<String, Object> param);
	public List<FRequestDAO> getFRequestList();
	public List<FRequestDAO> getFRequestDetail(Map<String, Object> param);
	public List<FRequestDAO> getFRequestDetailByReqNo(Map<String, Object> param);
}
