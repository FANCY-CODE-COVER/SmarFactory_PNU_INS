package com.pnu.spring.smartfactory.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pnu.spring.smartfactory.DAO.DataDAO;
import com.pnu.spring.smartfactory.Mapper.DataMapper;

@Service("com.pnu.spring.smartfactory.Service.DataServiceImpl")
public class DataServiceImpl implements DataService{
	@Autowired  
	private DataMapper dataMapper;
	public List<DataDAO> getDatasService(String category_id) {
		return dataMapper.getDatas(category_id);
	}
}
