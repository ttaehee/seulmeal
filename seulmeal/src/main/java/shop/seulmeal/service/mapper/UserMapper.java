
package shop.seulmeal.service.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import shop.seulmeal.common.Search;
import shop.seulmeal.service.domain.BlackList;
import shop.seulmeal.service.domain.Parts;
import shop.seulmeal.service.domain.Point;
import shop.seulmeal.service.domain.User;

@Mapper
public interface UserMapper {

	public int insertUser(User user) throws Exception;
	
	public int insertHatesParts(Parts parts) throws Exception;
	
	public User getUser(String userId) throws Exception;
	
	public int updateUser(User user) throws Exception;
	
	public int deleteUser(User user) throws Exception;
	
	public List<User> getListUser(Search search) throws Exception;
	
	public int checkDuplicationUserId(String userId) throws Exception;
	
	public int checkDuplicationNickName(String nickName) throws Exception;
		
	public int checkDuplicationPhone(String phone) throws Exception;
	
	public int checkDuplicationEmail(String email) throws Exception;
	
	public int getListUserTotalCount(Search search) throws Exception;
	
	public int updateUserGrade(int no) throws Exception;
	
	public int updateUserTotalPoint(int no) throws Exception;
	
	public int insertBlackList(BlackList blackList) throws Exception;
	
	public int updateBlackList(BlackList blackList) throws Exception;
	
	//public List<BlackList> getListBlackList(Search search) throws Exception;
	
	public int insertPoint(Point point) throws Exception;
	
	//public Point getPoint(Point point) throws Exception;
	
	public List<Point> getListPoint(Search search) throws Exception;
	
	public int getListPointTotalCount(int no) throws Exception;
	
	public User getProfile(User user) throws Exception;
	
	
	
	
	
}
