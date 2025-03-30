package io.github.skeffy.octave.dao;

import io.github.skeffy.octave.model.User;

public interface UserDao {

    User getUserByEmail(String email);

    User createUser(User newUser);

    int deleteUser(User user);

    int updateUser(User user);
}
