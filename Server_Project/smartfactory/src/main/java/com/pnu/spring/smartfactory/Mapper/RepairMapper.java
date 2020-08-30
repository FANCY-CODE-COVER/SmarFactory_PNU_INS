package com.pnu.spring.smartfactory.Mapper;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.InspectDAO;
import com.pnu.spring.smartfactory.DAO.RepairDAO;

public interface RepairMapper {
	public void insRepair(Map<String, Object> param);
	public void delRepair(Map<String, Object> param);
	public List<RepairDAO> getRepairList();
	public List<RepairDAO> getRepairDetail(Map<String, Object> param);
}
