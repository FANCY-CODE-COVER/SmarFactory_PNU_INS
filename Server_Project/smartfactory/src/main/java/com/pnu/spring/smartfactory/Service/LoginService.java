package com.pnu.spring.smartfactory.Service;

import java.util.List;
import java.util.Map;

import com.pnu.spring.smartfactory.DAO.LoginDAO;

public interface LoginService {
	public List<String> tryloginService(Map<String, Object> param);
}
