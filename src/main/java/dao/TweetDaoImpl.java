package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import model.Tweet;

/**
 * The class implement TweetDao interface, with all
 * needed methods of accessing data from database w.r.t to tweet table.
 * Using Autowired NamedParameterJdbcTemplate with DataSource accessing
 * in-memory database.
 * 
 * @author haoxu
 * @see TweetDao
 */
@Component
public class TweetDaoImpl implements TweetDao {
	
	/**
	 * @field auto wired field with database
	 */
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	/**
	 * create the namedParameterJdbcTemplate
	 * @param namedParameterJdbcTemplate
	 */
	@Autowired
    public TweetDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
	
	/* nested class used in listTweet method */
	private static final class TweetMapper implements RowMapper<Tweet> {

		public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Tweet tweet = new Tweet();
        	tweet.setId(rs.getInt("person_id"));
        	tweet.setTweet(rs.getString("content"));
        	return tweet;
        }
	}
	
	@Override
	public List<Tweet> listTweets(String sql, Map<String, Integer> map) {
		List<Tweet> tweets = this.namedParameterJdbcTemplate.query(sql, map, new TweetMapper());
		return tweets;
	}

}
