package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.RepairDAO;

public interface RepairService {
	public void insRepairService(Map<String, Object> param);
	public void delRepairService(Map<String, Object> param);
	public List<RepairDAO> getRepairListService();
	public List<RepairDAO> getRepairDetailService(Map<String, Object> param);
	public List<RepairDAO> getRepairDetailByRepairNoService(Map<String, Object> param);
}


