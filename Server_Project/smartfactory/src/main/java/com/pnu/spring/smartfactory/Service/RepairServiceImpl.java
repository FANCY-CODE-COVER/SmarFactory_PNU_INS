package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pnu.spring.smartfactory.DAO.RepairDAO;
import com.pnu.spring.smartfactory.Mapper.RepairMapper;
@Service("com.pnu.spring.smartfactory.Service.RepairServiceImpl")
public class RepairServiceImpl implements RepairService{
	@Autowired  
	private RepairMapper repairMapper;
	public void insRepairService(Map<String, Object> param) {
		repairMapper.insRepair(param);
	}
	public void delRepairService(Map<String, Object> param) {
		repairMapper.delRepair(param);
	}
	public List<RepairDAO> getRepairListService(){
		return repairMapper.getRepairList();
	}
	public List<RepairDAO> getRepairDetailService(Map<String, Object> param){
		return repairMapper.getRepairDetail(param);
	}
}
