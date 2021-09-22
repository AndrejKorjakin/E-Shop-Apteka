package mk.ukim.finki.wp.eLek.service;

import mk.ukim.finki.wp.eLek.model.User;

public interface AuthService {
    User login(String username, String password);
}
