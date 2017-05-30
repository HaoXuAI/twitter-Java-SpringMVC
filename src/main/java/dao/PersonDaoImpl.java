package dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import model.User;

/**
 * The class implement PersonDao interface, with all
 * needed methods of accessing data from database w.r.t to person table.
 * Using Autowired NamedParameterJdbcTemplate with DataSource accessing
 * in-memory database.
 * 
 * @author haoxu
 * @see PersonDao
 */
@Repository
public class PersonDaoImpl implements PersonDao {
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
    public PersonDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

	@Override
	public User getUser(String sql, Map<String, String> map) {
		try {
			List<User> res = this.namedParameterJdbcTemplate.query(sql, map, new UserMapper());
			return res.get(0);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public User getUserById(String sql, Map<String, Integer> map) {
		try {
			List<User> users = this.namedParameterJdbcTemplate.query(sql, map, new UserMapper());
			return users.get(0);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	private static final class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            return user;
        }
    }

	@Override
	public List<User> getAllUsers(String sql) {
		List<User> res = this.namedParameterJdbcTemplate.query(sql, new AllUserMapper());
		return res;
	}

	private static final class AllUserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            return user;
        }
    }
}
