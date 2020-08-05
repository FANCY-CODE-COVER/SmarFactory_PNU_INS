package com.pnu.spring.smartfactory.Service;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.DataDAO;

public interface DataService {
	public List<DataDAO> getDatasService(String category_id);
}
