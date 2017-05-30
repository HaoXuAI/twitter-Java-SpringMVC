package model;

public class Tweet {
	/**
	 * @field id the id of the person
	 */
	private int personId;
	
	/**
	 * @field message the content of the tweet
	 */
	private String message;
	
	/**
	 * create an empty tweet
	 */
	public Tweet() {		
	}
	
	/**
	 * create an tweet with specified person id and content
	 * @param id
	 * @param content
	 */
	public Tweet(int id, String content) {
		this.personId = id;
		this.message = content;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return this.personId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.personId = id;
	}
	/**
	 * @return the tweet
	 */
	public String getTweet() {
		return message;
	}
	/**
	 * @param tweet the tweet to set
	 */
	public void setTweet(String tweet) {
		this.message = tweet;
	}
}
