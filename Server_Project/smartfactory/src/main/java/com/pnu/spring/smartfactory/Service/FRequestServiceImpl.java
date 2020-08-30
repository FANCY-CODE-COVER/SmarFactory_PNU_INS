package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

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
	public void insFRequestSerivce(Map<String, Object> param) {
		frequestMapper.insFRequest(param);
	}
	public void delFRequestSerivce(Map<String, Object> param) {
		frequestMapper.delFRequest(param);
	}
	public List<FRequestDAO> getFRequestListSerivce(){
		return frequestMapper.getFRequestList();
	}
	public List<FRequestDAO> getFRequestDetailSerivce(Map<String, Object> param){
		return frequestMapper.getFRequestDetail(param);
	}
}
