package glit.onetable.mapper;

import org.apache.ibatis.annotations.Mapper;
import glit.onetable.model.vo.User;

@Mapper
public interface AuthMapper {
	
	/**
	 * User테이블 질의
	 * @return
	 */
	public User login(User user);
	
	/**
	 * 계정생성
	 * @param userDTO
	 * @throws Exception
	 */
	public void registerInsert(User user);
	
	/**
	 * 아이디 중복검사
	 * @param id
	 * @return 0, 1
	 */
	public int registerDuplicateId(String id);
	
	public int withdrawal(String userUUID);
	
	public User getUser(String token);

}
