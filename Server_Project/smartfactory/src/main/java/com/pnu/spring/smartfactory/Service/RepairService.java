package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.RepairDAO;

public interface RepairService {
	public void insRepairService(String repair_no,String reg_dt );
	public void delRepairService(String repair_no);
	public List<RepairDAO> getRepairListService();
	public List<RepairDAO> getRepairDetailService(Map<String, Object> param);
}
