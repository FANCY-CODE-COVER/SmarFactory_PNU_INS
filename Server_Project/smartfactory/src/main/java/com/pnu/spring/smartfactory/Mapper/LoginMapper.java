package com.pnu.spring.smartfactory.Mapper;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.LoginDAO;

public interface LoginMapper {
	public List<String> trylogin(Map<String, Object> param);
}
