package services;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.PersonDao;
import model.Follow;
import model.User;

/**
 * the object providing user service
 * @author haoxu
 *
 */
@Service
public class UserService {

	@Autowired
	private PersonDao userDaoImpl;
	
	@Autowired
	private FollowService followService;
	
	/**
	 * the method get user by the user name
	 * @param username the name of the user
	 * @return the user
	 */
	public User getUser(String username) {
		String sql = "SELECT * FROM PERSON WHERE NAME = :username";
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		User user = this.userDaoImpl.getUser(sql, map);
		return user;
	}
	
	/**
	 * the method get user by id
	 * @param id the id of the user
	 * @return the user
	 */
	public User getUserById(int id) {
		String sql = "SELECT * FROM PERSON WHERE ID= :id";
		Map<String, Integer> map = new HashMap<>();
		map.put("id", id);
		User user = this.userDaoImpl.getUserById(sql, map);
		return user;
	}
	
	/**
	 * the method get all users in database
	 * @return the list of all users
	 */
	public List<User> getAllUsers() {
		String sql = "SELECT * FROM person";
		List<User> res = this.userDaoImpl.getAllUsers(sql);
		return res;
	}

	/**
	 * 
	 * @param srcUser is the user 
	 * @param username the name of the target user
	 * @return the path from the user to the target user
	 * @throws Exception unacceptable error
	 */
	public List<String> getMinPath(User srcUser, String username) throws Exception {

		Map<User, Boolean> vis = new HashMap<User, Boolean>();
		Map<String, String> prev = new HashMap<String, String>();
		Queue<User> queue = new LinkedList<User>();
		
		queue.add(srcUser);
		vis.put(srcUser, true);

		if (srcUser == null || srcUser.getName() == null) {
			throw new Exception("the request user is not in database");
		}
		while (!queue.isEmpty()) {
			User user = queue.poll();
			List<Follow> followingUrs = this.followService.getFollowingUsers(user.getName());
			if (followingUrs == null) {
				throw new Exception("the reuquest user does not have follow relation");
			}
			for (Follow follow : followingUrs) {
				int adjUserId = follow.getPerson_id();
				User followingUser = this.getUserById(adjUserId);
				prev.put(followingUser.getName(), user.getName());
				if (followingUser.getName().equals(username)) {
					return getPath(prev, username);
				} else if (!vis.containsKey(followingUser)) {
					queue.offer(followingUser);
					vis.put(followingUser, true);
				}
			}
		}
		throw new Exception("Could not find the path");
	}
	
	/**
	 * the helper method to find the path by reversing the route
	 * @param prev the map storing the following relation in reverse order
	 * @param username the target user name
	 * @return the path
	 */
	private List<String> getPath(Map<String, String> prev, String username) {
		List<String> directions = new LinkedList<>();
		for(String user = username; user != null; user = prev.get(user)) {
	        directions.add(user);
	    }
		Collections.reverse(directions);
		return directions;
	}
}
