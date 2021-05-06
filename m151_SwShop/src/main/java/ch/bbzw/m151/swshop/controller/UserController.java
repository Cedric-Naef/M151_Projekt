package ch.bbzw.m151.swshop.controller;

import ch.bbzw.m151.swshop.model.User;
import ch.bbzw.m151.swshop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/users")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class UserController {

    private final UserRepo userRepo;

    @Autowired
    public UserController(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/info")
    public Authentication getInfo() {
        final SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAll() {
        final Iterable<User> users = userRepo.findAll();
        return StreamSupport
                .stream(users.spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User add(@RequestBody final User user) {
        return userRepo.save(user);
    }
}
