package com.pnu.spring.smartfactory.Mapper;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.FRequestDAO;
import com.pnu.spring.smartfactory.DAO.InspectDAO;

public interface InspectMapper {
	public void insInspect(String insp_rst_no,String insp_date );
	public void delInspect(String insp_rst_no);
	public List<InspectDAO> getInspectList();
	public List<InspectDAO> getInspectDetail(String facility_no);
}
