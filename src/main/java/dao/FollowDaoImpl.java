package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import model.Follow;

/**
 * The class implement FollowDao interface, with all
 * needed methods of accessing data from database w.r.t follow table.
 * Using Autowired NamedParameterJdbcTemplate with DataSource accessing
 * in-memory database.
 * 
 * @author haoxu
 * @see FollowDao
 */
@Repository
public class FollowDaoImpl implements FollowDao {
	/**
	 * @field auto wired with database
	 */
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	/**
	 * create the namedParameterJdbcTemplate
	 * @param namedParameterJdbcTemplate
	 */
	@Autowired
    public FollowDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

	@Override
	public boolean search(String sql, Map<String, Integer> map) {
		List<Follow> list = this.namedParameterJdbcTemplate.query(sql, map, new FollowMapper());
		return !list.isEmpty();
	}
	
	@Override
	public int follow(String sql, Map<String, Integer> map) {
		try {
			return this.namedParameterJdbcTemplate.update(sql, map);
		} catch (DataIntegrityViolationException e) {
			return 0;
		}
	}

	@Override
	public int unfollow(String sql, Map<String, Integer> map) {
		try {
			return this.namedParameterJdbcTemplate.update(sql, map);
		} catch (DataIntegrityViolationException e) {
			return 0;
		}
	}

	@Override
	public List<Follow> listFollowing(String sql, Map<String, String> map) {
		List<Follow> res = this.namedParameterJdbcTemplate.query(sql, map, new FollowMapper());
		if(res == null || res.size() == 0) {
            return null;
        }
		return res;
	}

	@Override
	public List<Follow> listFollowers(String sql, Map<String, String> map) {
		List<Follow> followers = this.namedParameterJdbcTemplate.query(sql, map, new FollowMapper());
		return followers;
	}
	
	@Override
	public List<Map<Integer, Integer>> getPopular(String sql) {
		List<Map<Integer, Integer>> res = this.namedParameterJdbcTemplate.query(sql, new PopularMapper());
		return res;
	}
	
	
	private static final class FollowMapper implements RowMapper<Follow> {
        public Follow mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Follow follow = new Follow();
        	follow.setFollower_id(rs.getInt("follower_person_id"));
        	follow.setPerson_id(rs.getInt("person_id"));
        	return follow;
        }
    }
	
	private static final class PopularMapper implements RowMapper<Map<Integer, Integer>> {
        public Map<Integer, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Map<Integer, Integer> res = new HashMap<>();
    		res.put(rs.getInt("person_id"), rs.getInt("follower_person_id"));
        	return res;
        }
    }
	
}
