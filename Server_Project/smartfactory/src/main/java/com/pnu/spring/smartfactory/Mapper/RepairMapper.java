package com.pnu.spring.smartfactory.Mapper;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.InspectDAO;
import com.pnu.spring.smartfactory.DAO.RepairDAO;

public interface RepairMapper {
	public void insRepair(String repair_no,String reg_dt );
	public void delRepair(String repair_no);
	public List<RepairDAO> getRepairList();
	public List<RepairDAO> getRepairDetail(Map<String, Object> param);
}
