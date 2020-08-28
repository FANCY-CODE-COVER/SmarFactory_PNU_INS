package com.pnu.spring.smartfactory.Mapper;

import com.pnu.spring.smartfactory.DAO.FRequestDAO;
import com.pnu.spring.smartfactory.DAO.ResponseDAO;
import java.util.List;

public interface FRequestMapper {
	public void insFRequest(String req_no,String req_dt );
	public void delFRequest(String req_no);
	public List<FRequestDAO> getFRequestList();
	public List<FRequestDAO> getFRequestDetail(String facility_no);
}
