package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.FollowDao;
import model.Follow;
import model.User;

/**
 * the object of providing relavent follow service
 * @author haoxu
 *
 */
@Service
public class FollowService {
	/**
	 * the object accessing data in database
	 */
	@Autowired
	private FollowDao followDaoImpl;

	/**
	 * @return the followDaoImpl
	 */
	public FollowDao getFollowDaoImpl() {
		return followDaoImpl;
	}

	/**
	 * @param followDaoImpl the followDaoImpl to set
	 */
	public void setFollowDaoImpl(FollowDao followDaoImpl) {
		this.followDaoImpl = followDaoImpl;
	}
	
	/**
	 * the method to create a following relation
	 * @param userFollowed the user being followed
	 * @param userFollower the follower user
	 * @return 1 for following success; 0 for failing
	 */
	public int followUser(User userFollowed, User userFollower) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userFollowedId", userFollowed.getId());
		map.put("userFollowerId", userFollower.getId());
		String searchSql = "SELECT * FROM followers WHERE person_id = :userFollowedId "
				+ "AND follower_person_id = :userFollowerId";
		if (this.followDaoImpl.search(searchSql, map)) {
			return -1;
		}
		String sql = "INSERT INTO followers (person_id, follower_person_id) VALUES (:userFollowedId, :userFollowerId)";
		
		return this.followDaoImpl.follow(sql, map);
	}
	
	/**
	 * the method of unfollowing a user
	 * @param userFollowed the user being followed
	 * @param userFollower the follower user
	 * @return 1 for following success; 0 for fail;
	 */
	public int unfollowUser(User userFollowed, User userFollower) {
		String sql = "DELETE FROM followers WHERE person_id = :userFollowedId " + "AND follower_person_id = :userFollowerId";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userFollowedId", userFollowed.getId());
		map.put("userFollowerId", userFollower.getId());
		return this.followDaoImpl.unfollow(sql, map);
		
	}
	
	/**
	 * the method of getting all followed users of the user
	 * @param username the name of the user
	 * @return the list of user followed person
	 */
	public List<Follow> getFollowingUsers(String username) {
		String sql = "SELECT * FROM followers WHERE follower_person_id = SELECT ID FROM Person WHERE NAME = :username";
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		List<Follow> followingUsers =  this.followDaoImpl.listFollowing(sql, map);
		return followingUsers;
	}
	
	/**
	 * the method of getting all followers of the user
	 * @param username the name of the user
	 * @return the list of followers of the user
	 */
	public List<Follow> getFollowerUsers(String username) {
		String sql = "SELECT * FROM followers WHERE person_id = SELECT ID FROM Person WHERE NAME = :username";
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		List<Follow> followers =  this.followDaoImpl.listFollowers(sql, map);
		return followers;
	}
	
	/**
	 * the method of pairing popular users
	 * @return the list of paired users and their popular followers
	 */
	public List<Map<Integer, Integer>> getpopularUrs() {
		String sql = "select * from (select followers.person_id, follower_person_id, popular.count from followers "
				+ "inner join (SELECT person_id, COUNT(follower_person_id) AS count FROM followers group by person_id) "
				+ "as popular on followers.follower_person_id = popular.person_id) order by person_id Asc, count desc";
		
		List<Map<Integer, Integer>> res = this.followDaoImpl.getPopular(sql);
		return res;
	}
}
