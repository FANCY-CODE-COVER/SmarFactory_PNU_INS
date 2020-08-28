package com.pnu.spring.smartfactory.Service;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.InspectDAO;

public interface InspectService {
	public void insInspectService(String insp_rst_no,String insp_date );
	public void delInspectService(String insp_rst_no);
	public List<InspectDAO> getInspectListService();
	public List<InspectDAO> getInspectDetailService(String facility_no);
}
