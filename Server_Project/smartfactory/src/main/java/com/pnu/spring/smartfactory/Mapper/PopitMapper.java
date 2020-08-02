package com.pnu.spring.smartfactory.Mapper;

import java.util.List; // awt.List가 아닌 Util.List를 사용하여야 합니다.

import com.pnu.spring.smartfactory.DAO.PopitDAO;

public interface PopitMapper {
	public List<PopitDAO> selectlist();
}
