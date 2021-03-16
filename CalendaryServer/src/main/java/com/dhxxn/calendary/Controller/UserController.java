package com.dhxxn.calendary.Controller;

import com.dhxxn.calendary.DTO.User;
import com.dhxxn.calendary.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        return userRepository.findById(id).get();
    }

    @PostMapping("/")
    public User create(@ModelAttribute User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id, @RequestBody User updateUser) {
        User user = userRepository.findById(id).get();
        if (updateUser.getName() != null) user.setName(updateUser.getName());
        if (updateUser.getEmail() != null) user.setName(updateUser.getEmail());
        if (updateUser.getPassword() != null) user.setPassword(updateUser.getPassword());
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userRepository.delete(userRepository.getOne(id));
    }
}

