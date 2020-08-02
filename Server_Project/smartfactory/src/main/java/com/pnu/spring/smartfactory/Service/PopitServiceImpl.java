package com.pnu.spring.smartfactory.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pnu.spring.smartfactory.DAO.PopitDAO;
import com.pnu.spring.smartfactory.Mapper.PopitMapper;
@Service("com.pnu.spring.smartfactory.Service.PopitServiceImpl") // �ش� Ŭ������ Service Class���� ����� �ݴϴ�.
public class PopitServiceImpl implements PopitService{ 
	@Autowired // �ڵ����� ������̼����μ�, Mapper InterFace�� �ڷ����Դϴ�. 
	private PopitMapper popitMapper;
        // Service Interface�� �Լ��� ���ȭ�� ������, �ش� select �۾��� �� �� DB�� ���� ��ȯ�ϴ� ���Դϴ�.
	public List<PopitDAO> selectlistService(){
		return popitMapper.selectlist();
	};
}