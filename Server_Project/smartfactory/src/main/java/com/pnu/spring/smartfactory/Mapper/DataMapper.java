package com.pnu.spring.smartfactory.Mapper;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.DataDAO;

public interface DataMapper {
	public List<DataDAO> getDatas(String category_id);
}
