package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.TweetDao;
import model.Follow;
import model.Tweet;
import model.User;

/**
 * the object providing service for tweets
 * @author haoxu
 *
 */
@Service
public class TwitterService {
	@Autowired
	private UserService userService;
	@Autowired
	private FollowService followservice;
	@Autowired
	private TweetDao twitterDaoImpl;
	
	/**
	 * the method of get all tweets of the user and users the user followed
	 * with no search option
	 * @param username the name of the user
	 * @return all tweets
	 */
	public Map<String, List<Tweet>> getMessage(String username) {
		User user = this.userService.getUser(username);
		int userId = user.getId();
		
		String userSql = "SELECT * FROM tweet WHERE person_id = :userId";
		
		Map<String, Integer> map = new HashMap<>();
		map.put("userId", userId);
		List<Tweet> myTweets = this.twitterDaoImpl.listTweets(userSql, map);
		
		Map<String, List<Tweet>> tweetMap = new HashMap<>();
		tweetMap.put(username, myTweets);
		
		/* get following users tweets */
		List<Follow> followingUrs = this.followservice.getFollowingUsers(username);
		for (Follow follow : followingUrs) {
			userId = follow.getPerson_id();
			User followingUser = this.userService.getUserById(userId);
			map.put("userId", userId);
			List<Tweet> followingTweets = this.twitterDaoImpl.listTweets(userSql, map);
			
			tweetMap.put(followingUser.getName(), followingTweets);
		}

		return tweetMap;
	}
	
	/**
	 * the method of get all tweets of the user and users the user followed
	 * with search option
	 * @param username
	 * @param search
	 * @return
	 */
	public Map<String, List<Tweet>> getMessage(String username, String search) {
		User user = this.userService.getUser(username);
		int userId = user.getId();
		String userSql = "SELECT * FROM tweet WHERE person_id = :userId AND CONTENT LIKE '%" + search +"%'";
		
		Map<String, Integer> map = new HashMap<>();
		map.put("userId", userId);
		List<Tweet> myTweets = this.twitterDaoImpl.listTweets(userSql, map);
		
		Map<String, List<Tweet>> tweetMap = new HashMap<>();
		tweetMap.put(username, myTweets);
		
		/* get following users tweets */
		List<Follow> followingUrs = this.followservice.getFollowingUsers(username);
		for (Follow follow : followingUrs) {
			userId = follow.getPerson_id();
			User followingUser = this.userService.getUserById(userId);
			map.put("userId", userId);
			List<Tweet> followingTweets = this.twitterDaoImpl.listTweets(userSql, map);
			
			tweetMap.put(followingUser.getName(), followingTweets);
			
		}

		return tweetMap;
	}
}
