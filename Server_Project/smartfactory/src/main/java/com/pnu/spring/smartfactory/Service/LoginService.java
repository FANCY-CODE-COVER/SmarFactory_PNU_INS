package com.pnu.spring.smartfactory.Service;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.LoginDAO;

public interface LoginService {
	public List<LoginDAO> tryloginService(String user_id, String password);
}
