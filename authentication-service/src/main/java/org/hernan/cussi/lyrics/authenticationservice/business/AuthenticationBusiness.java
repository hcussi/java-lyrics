package org.hernan.cussi.lyrics.authenticationservice.business;

import org.hernan.cussi.lyrics.authenticationservice.dto.AuthRequest;
import org.hernan.cussi.lyrics.authenticationservice.exception.InvalidUserCredentialsException;
import org.hernan.cussi.lyrics.authenticationservice.exception.UserNotFoundException;
import org.hernan.cussi.lyrics.authenticationservice.model.User;
import org.hernan.cussi.lyrics.authenticationservice.repository.UserRepository;
import org.hernan.cussi.lyrics.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationBusiness {

  private final JwtUtil jwtUtil;

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  @Autowired
  public AuthenticationBusiness(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public String getToken(AuthRequest authRequest) throws UserNotFoundException {
    User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(UserNotFoundException::new);

    if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
      throw new InvalidUserCredentialsException("Invalid email or password");
    }

    var token = jwtUtil.generateToken(authRequest.getEmail());
    jwtUtil.validateToken(token, user.getEmail());

    return token;

  }

}
