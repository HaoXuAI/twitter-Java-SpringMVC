package model;

public class Follow {
	/**
	 * @field person_id is the following person id
	 */
	private int person_id;
	
	/**
	 * @field follower_id is the follower person id
	 */
	private int follower_id;
	
	/**
	 * create an empty object of the follow relation
	 */
	public Follow() {		
	}
	
	/**
	 * create a object with specified user and follower
	 * @param person_id the person id being followed
	 * @param follower_id the follower person id
	 */
	public Follow(int person_id, int follower_id) {
		this.person_id = person_id;
		this.follower_id = follower_id;
	}
	
	/**
	 * @return the person_id
	 */
	public int getPerson_id() {
		return person_id;
	}
	/**
	 * @param person_id the person_id to set
	 */
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	/**
	 * @return the follower_id
	 */
	public int getFollower_id() {
		return follower_id;
	}
	/**
	 * @param follower_id the follower_id to set
	 */
	public void setFollower_id(int follower_id) {
		this.follower_id = follower_id;
	}
	
	
}
