package com.pnu.spring.smartfactory.Mapper;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.LoginDAO;

public interface LoginMapper {
	public List<LoginDAO> trylogin(String user_id, String password);
}
