package com.pnu.spring.smartfactory.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pnu.spring.smartfactory.DAO.InspectDAO;
import com.pnu.spring.smartfactory.Mapper.InspectMapper;
@Service("com.pnu.spring.smartfactory.Service.InspectServiceImpl")
public class InspectServiceImpl implements InspectService{
	@Autowired  
	private InspectMapper inspectMapper;
	public void insInspectService(String insp_rst_no,String insp_date ) {
		inspectMapper.insInspect(insp_rst_no, insp_date);
	}
	public void delInspectService(String insp_rst_no) {
		inspectMapper.delInspect(insp_rst_no);
	}
	public List<InspectDAO> getInspectListService(){
		return inspectMapper.getInspectList();
	}
	public List<InspectDAO> getInspectDetailService(String facility_no){
		return inspectMapper.getInspectDetail(facility_no);
	}

}
