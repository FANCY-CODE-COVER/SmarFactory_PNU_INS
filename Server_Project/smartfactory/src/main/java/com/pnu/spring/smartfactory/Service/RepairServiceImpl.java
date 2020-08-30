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
	public void insRepairService(String repair_no,String reg_dt ) {
		repairMapper.insRepair(repair_no, reg_dt);
	}
	public void delRepairService(String repair_no) {
		repairMapper.delRepair(repair_no);
	}
	public List<RepairDAO> getRepairListService(){
		return repairMapper.getRepairList();
	}
	public List<RepairDAO> getRepairDetailService(Map<String, Object> param){
		return repairMapper.getRepairDetail(param);
	}
}
