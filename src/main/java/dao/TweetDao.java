package dao;

import java.util.List;
import java.util.Map;

import model.Tweet;

/**
 * An interface to access the tweet table in database 
 */
public interface TweetDao {

	/**
	 * Method listing all tweets of the user and following users
	 * the user following
	 * @param sql the query accessing the data in database
	 * @param map mapping feature in sql with user name/id
	 * @return the list of Tweet model 
	 */
	List<Tweet> listTweets(String sql, Map<String, Integer> map);

}
