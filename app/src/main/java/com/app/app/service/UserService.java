package com.app.app.service;

import com.app.app.dao.RoleDao;
import com.app.app.dao.UserDao;
import com.app.app.entity.Role;
import com.app.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user = new User();
        user.setUserName("gafsaoui");
        user.setUserPassword(getEncodedPassword("gafsaoui"));
        user.setUserFirstName("raj");
        user.setUserLastName("sharma");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);
    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRole(roleSet);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public long getUsersConnectedToday() {
        LocalDate today = LocalDate.now();
        return userDao.countByLastLoginDateGreaterThan(today.atStartOfDay());
    }

    public Map<String, Long> getUserConnections() {
        LocalDate startDate = LocalDate.now().minusDays(6);
        List<User> users = userDao.findAll();
        Map<String, Long> connections = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            long count = users.stream()
                    .filter(u -> u.getLastLoginDate() != null && u.getLastLoginDate().toLocalDate().equals(date))
                    .count();
            connections.put(date.toString(), count);
        }
        return connections;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }


    public Map<String, Long> getUserCategories() {
        List<User> users = userDao.findAll();
        Map<String, Long> categories = new HashMap<>();
        users.forEach(user -> {
            user.getRole().forEach(role -> {
                categories.merge(role.getRoleName(), 1L, Long::sum);
            });
        });
        return categories;
    }
}