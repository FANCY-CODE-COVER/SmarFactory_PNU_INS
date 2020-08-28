package com.pnu.spring.smartfactory.Service;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.FRequestDAO;
import com.pnu.spring.smartfactory.DAO.ResponseDAO;

public interface FRequestService {
	public void insFRequestSerivce(String req_no,String req_dt );
	public void delFRequestSerivce(String req_no);
	public List<FRequestDAO> getFRequestListSerivce();
	public List<FRequestDAO> getFRequestDetailSerivce(String facility_no);
}
