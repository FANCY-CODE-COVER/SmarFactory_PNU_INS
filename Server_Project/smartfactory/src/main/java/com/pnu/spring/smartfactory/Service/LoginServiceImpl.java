package com.pnu.spring.smartfactory.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pnu.spring.smartfactory.DAO.LoginDAO;
import com.pnu.spring.smartfactory.Mapper.LoginMapper;
@Service("com.pnu.spring.smartfactory.Service.LoginServiceImpl") // 해당 클래스가 Service Class임을 명시해 줍니다.
public class LoginServiceImpl implements LoginService{
	@Autowired // 자동주입 어노테이션으로서, Mapper InterFace가 자료형입니다. 
	private LoginMapper loginMapper;
        // Service Interface의 함수를 명시화한 것으로, 해당 select 작업을 한 후 DB의 값을 반환하는 것입니다.
	public List<LoginDAO> tryloginService(String user_id, String password){
		return loginMapper.trylogin(user_id, password);
	}
}
