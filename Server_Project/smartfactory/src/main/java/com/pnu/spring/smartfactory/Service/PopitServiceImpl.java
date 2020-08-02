package com.pnu.spring.smartfactory.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pnu.spring.smartfactory.DAO.PopitDAO;
import com.pnu.spring.smartfactory.Mapper.PopitMapper;
@Service("com.pnu.spring.smartfactory.Service.PopitServiceImpl") // 해당 클래스가 Service Class임을 명시해 줍니다.
public class PopitServiceImpl implements PopitService{ 
	@Autowired // 자동주입 어노테이션으로서, Mapper InterFace가 자료형입니다. 
	private PopitMapper popitMapper;
        // Service Interface의 함수를 명시화한 것으로, 해당 select 작업을 한 후 DB의 값을 반환하는 것입니다.
	public List<PopitDAO> selectlistService(){
		return popitMapper.selectlist();
	};
}