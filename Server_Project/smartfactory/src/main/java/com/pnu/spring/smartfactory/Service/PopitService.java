package com.pnu.spring.smartfactory.Service;

import java.util.List;

import com.pnu.spring.smartfactory.DAO.PopitDAO;

public interface PopitService {
	// selectList�� �����ϰ� �̸��� ��� ���������. ������ ���� Service�� �ٿ����ϴ�.
	public List<PopitDAO> selectlistService();
}
