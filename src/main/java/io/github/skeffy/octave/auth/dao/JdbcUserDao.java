package io.github.skeffy.octave.auth.dao;

import io.github.skeffy.octave.auth.exception.DaoException;
import io.github.skeffy.octave.auth.model.User;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Objects;

@Component
public class JdbcUserDao implements UserDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                user = mapRowToUser(results);
            } else {
                return null;
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, email);
            if (results.next()) {
                user = mapRowToUser(results);
            } else {
                return null;
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return user;
    }

    @Override
    public User createUser(User newUser) {
        return null;
    }

    @Override
    public int deleteUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    private User mapRowToUser(SqlRowSet results) {
        User user = new User();
        user.setId(results.getString("user_id"));
        user.setUsername(results.getString("username"));
        user.setName(results.getString("name"));
        user.setEmail(results.getString("email"));
        user.setBio(results.getString("bio"));
        user.setAuthorities(Objects.requireNonNull(results.getString("role")));
        return user;
    }
}
