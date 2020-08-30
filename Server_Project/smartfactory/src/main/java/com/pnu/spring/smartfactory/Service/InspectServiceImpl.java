package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pnu.spring.smartfactory.DAO.InspectDAO;
import com.pnu.spring.smartfactory.Mapper.InspectMapper;
@Service("com.pnu.spring.smartfactory.Service.InspectServiceImpl")
public class InspectServiceImpl implements InspectService{
	@Autowired  
	private InspectMapper inspectMapper;
	public void insInspectService(Map<String, Object> param) {
		inspectMapper.insInspect(param);
	}
	public void delInspectService(Map<String, Object> param) {
		inspectMapper.delInspect(param);
	}
	public List<InspectDAO> getInspectListService(){
		return inspectMapper.getInspectList();
	}
	public List<InspectDAO> getInspectDetailService(Map<String, Object> param){
		return inspectMapper.getInspectDetail(param);
	}

}
