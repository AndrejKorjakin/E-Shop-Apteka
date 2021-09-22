package mk.ukim.finki.wp.eLek.service;

import mk.ukim.finki.wp.eLek.model.Role;
import mk.ukim.finki.wp.eLek.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
