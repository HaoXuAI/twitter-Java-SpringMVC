package dao;

import java.util.List;
import java.util.Map;

import model.Follow;

/**
 * an interface to access the follower table in database 
 */
public interface FollowDao {
	/** 
	 * method to follow a user 
	 * @return 1 for follow success, 0 for fail
	 * @param sql accessing operation to database
	 * @param map feature in sql to the user name/id
	 */
	public int follow(String sql, Map<String, Integer> map);

	/** 
	 * method to unfollow a user 
	 * @return 1 for follow success, 0 for fail
	 * @param sql query accessing operation to database
	 * @param map feature in sql to the user name/id
	 */
	public int unfollow(String sql, Map<String, Integer> map);
	
	/** 
	 * method to list following user of the user 
	 * @return the list of Follow model with person_id is the following
	 * user name
	 * @param sql query accessing operation to database
	 * @param map feature in sql to the user name/id
	 */
	List<Follow> listFollowing(String sql, Map<String, String> map);
	
	/** 
	 * method to list follower user of the user
	 * @return the list of Follow model with person_id is the follower
	 * user name
	 * @param sql query accessing operation to database
	 * @param map feature in sql to the user name/id
	 */
	List<Follow> listFollowers(String sql, Map<String, String> map);

	/** 
	 * method to get popular follower of the user
	 * @return the pair of users and their most popular followers
	 * @param sql query accessing operation to database
	 * @param map feature in sql to the user name/id
	 */
	List<Map<Integer, Integer>> getPopular(String sql);

	/**
	 * method to search if the follow relation exist in database
	 * @param searchSql the sql to search the relation
	 * @param map mapping the feature in sql to user name/id
	 * @return true if exist; false if not
	 */
	public boolean search(String searchSql, Map<String, Integer> map);
}
