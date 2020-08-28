package com.pnu.spring.smartfactory.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pnu.spring.smartfactory.DAO.FRequestDAO;
import com.pnu.spring.smartfactory.DAO.ResponseDAO;
import com.pnu.spring.smartfactory.Mapper.FRequestMapper;
import com.pnu.spring.smartfactory.Mapper.FacilityMapper;
@Service("com.pnu.spring.smartfactory.Service.FRequestServiceImpl")
public class FRequestServiceImpl implements FRequestService{
	@Autowired  
	private FRequestMapper frequestMapper;
	public void insFRequestSerivce(String req_no,String req_dt ) {
		frequestMapper.insFRequest(req_no,req_dt );
	}
	public void delFRequestSerivce(String req_no) {
		frequestMapper.delFRequest(req_no);
	}
	public List<FRequestDAO> getFRequestListSerivce(){
		return frequestMapper.getFRequestList();
	}
	public List<FRequestDAO> getFRequestDetailSerivce(String facility_no){
		return frequestMapper.getFRequestDetail(facility_no);
	}
}
