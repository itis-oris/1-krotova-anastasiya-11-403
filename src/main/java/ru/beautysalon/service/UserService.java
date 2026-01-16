package ru.beautysalon.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.beautysalon.dao.*;
import ru.beautysalon.model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public class UserService {
    private UserDao userDao;
    private MasterDao masterDao;
    private PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, MasterDao masterDao) {
        this.userDao = userDao;
        this.masterDao = masterDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> user = userDao.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPasswordHash())) {
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }

    public boolean registerUser(String email, String password, String firstName, String lastName, String phone) {
        if (userDao.findByEmail(email).isPresent()) return false;

        String hash = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(hash);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setPhone(phone);
        user.setRole(User.UserRole.USER);
        user.setCreated_at(LocalDateTime.now());

        try {
            userDao.save(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateUser(Long id, String firstName, String lastName, String phone) {
        Optional<User> userOpt = userDao.findById(id);
        if (!userOpt.isPresent()) return false;

        User user = userOpt.get();
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setPhone(phone);

        try {
            userDao.update(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteUser(Long id) {
        Optional<Master> master = masterDao.findByUserId(id);
        if (master.isPresent()) {
            try {
                masterDao.delete(master.get().getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            userDao.delete(id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<User> getAllClients() {
        return userDao.findAllByRole(User.UserRole.USER);
    }

}
