package com.spring.moji.service;


import com.spring.moji.security.CustomerUserDetail;
import com.spring.moji.entity.UserAuth;
import com.spring.moji.entity.User;
import com.spring.moji.mapper.RequestMapper;
import com.spring.moji.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;
  private final RequestMapper requestMapper;

  @Override
  @Transactional
  public int join(User user, Model model) throws Exception {
    // 필수 항목 체크
    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      model.addAttribute("errorMessage", "이메일은 필수 항목입니다.");
      return 0; // 오류 발생 시 회원가입 처리 중단
    }
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      model.addAttribute("errorMessage", "비밀번호는 필수 항목입니다.");
      return 0;
    }
    if (user.getUserName() == null || user.getUserName().isEmpty()) {
      model.addAttribute("errorMessage", "이름은 필수 항목입니다.");
      return 0;
    }
    if (user.getBirthday() == null) {
      model.addAttribute("errorMessage", "생일은 필수 항목입니다.");
      return 0;
    }
    if (user.getGender() == null || user.getGender().isEmpty()) {
      model.addAttribute("errorMessage", "성별은 필수 항목입니다.");
      return 0;
    }

    // 비밀번호 암호화
    String userPassword = user.getPassword();
    String encodedUserPassword = passwordEncoder.encode(userPassword);
    user.setPassword(encodedUserPassword);

    // 회원 등록
    int result = userMapper.join(user);

    if (result > 0) {
      UserAuth userAuth = new UserAuth();
      userAuth.setUserEmail(user.getEmail());
      userAuth.setAuth("ROLE_SOLO");
      result += userMapper.insertAuth(userAuth);
    }

    return result;
  }

  @Override
  public int updateProfileImageUrl(String email, String profileImageUrl, HttpSession session)
      throws Exception {
    int result = userMapper.updateProfileImageUrl(email, profileImageUrl);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
      CustomerUserDetail currentUser = (CustomerUserDetail) auth.getPrincipal();
      currentUser.getUser().setProfileImageUrl(profileImageUrl);
      UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
          currentUser, auth.getCredentials(), auth.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(newAuth);
      session.setAttribute("profileImageUrl", profileImageUrl);
      log.info("Profile image URL updated in session and authentication object");
    }
    return result;
  }

  @Override
  public int updateCoupleProfile(Long coupleId
      , String coupleName
      , String profileImageUrl
      , HttpSession session)
      throws Exception {
    int result = userMapper.updateCoupleProfile(coupleId, profileImageUrl,
        coupleName); // 여기에 바꿀 커플 이름과 url을 넣어줘야 함

    // 세션 정보를 바꾸는 부분
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null && auth.isAuthenticated()) {

      CustomerUserDetail currentUser = (CustomerUserDetail) auth.getPrincipal();
      currentUser.getCouple().setCoupleProfileImage(profileImageUrl);
      currentUser.getCouple().setCoupleName(coupleName);

      UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
          currentUser, auth.getCredentials(), auth.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(newAuth);

      session.setAttribute("coupleProfileImage", profileImageUrl);
      session.setAttribute("coupleName", coupleName);
    }
    return result;
  }

  @Override
  public User findPartner(String email) throws Exception {
    return userMapper.findPartner(email);
  }

  @Override
  public boolean isEmailAvailable(String email) throws Exception {
    return userMapper.countByEmail(email) == 0;
  }


}
