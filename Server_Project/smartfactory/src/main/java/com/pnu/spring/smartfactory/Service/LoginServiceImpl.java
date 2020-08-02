package com.pnu.spring.smartfactory.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pnu.spring.smartfactory.DAO.LoginDAO;
import com.pnu.spring.smartfactory.Mapper.LoginMapper;
@Service("com.pnu.spring.smartfactory.Service.LoginServiceImpl") // �ش� Ŭ������ Service Class���� ����� �ݴϴ�.
public class LoginServiceImpl implements LoginService{
	@Autowired // �ڵ����� ������̼����μ�, Mapper InterFace�� �ڷ����Դϴ�. 
	private LoginMapper loginMapper;
        // Service Interface�� �Լ��� ���ȭ�� ������, �ش� select �۾��� �� �� DB�� ���� ��ȯ�ϴ� ���Դϴ�.
	public List<LoginDAO> tryloginService(String user_id, String password){
		return loginMapper.trylogin(user_id, password);
	}
}
