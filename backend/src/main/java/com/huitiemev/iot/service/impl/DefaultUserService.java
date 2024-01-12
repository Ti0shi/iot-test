package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.User;
import com.huitiemev.iot.repository.UserRepository;
import com.huitiemev.iot.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    @Inject
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userRepository.persist(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.getEntityManager().find(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getEntityManager().createQuery("SELECT u FROM users u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.getEntityManager().createQuery("SELECT u FROM users u WHERE u.phoneNumber = :phoneNumber", User.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.getEntityManager().createQuery("SELECT u FROM users u WHERE u.email = :email AND u.password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.listAll();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.getEntityManager().merge(user);
    }
}
