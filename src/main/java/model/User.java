package model;

public class User {
	
	/**
	 * @field the name of the user
	 */
	private String name;
	
	/**
	 * the id of the user
	 */
	private int id;
	
	/**
	 * create an empty user
	 */
	public User() {
	}
	
	/**
	 * create an use with id and name
	 * @param user_id
	 * @param username
	 */
	public User(int user_id, String username) {
		this.name = username;
		this.id = user_id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
