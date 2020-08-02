package com.pnu.spring.smartfactory.Service;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.PopitDAO;

public interface PopitService {
	// selectList와 동일하게 이름을 적어도 상관없으나. 구분을 위해 Service를 붙였습니다.
	public List<PopitDAO> selectlistService();
}
