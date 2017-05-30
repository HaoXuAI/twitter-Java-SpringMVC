package dao;

import java.util.List;
import java.util.Map;

import model.User;

/*
 * An interface to access the Person table in database 
 */
public interface PersonDao {
	
	/**
	 * Method to access user data by user id in database 
	 * @param sql the query to accessing database
	 * @param map mapping the feature in sql with user name/id
	 * @return the model User
	 */
	public User getUserById(String sql, Map<String, Integer> map);

	/**
	 * Method to access user data by user name in database
	 * @param sql the query to accessing database
	 * @param map mapping the feature in sql with user name/id
	 * @return the model User 
	 */
	public User getUser(String sql, Map<String, String> map);

	/**
	 * Method to access user data in database
	 * @param sql the query to accessing database
	 * @param map mapping the feature in sql with user name/id
	 * @return all user data in database
	 */
	public List<User> getAllUsers(String sql);

}
