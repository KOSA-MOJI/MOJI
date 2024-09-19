package com.spring.moji.service;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.entity.UserAuthEntity;
import com.spring.moji.entity.UserEntity;
import com.spring.moji.mapper.UserMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserEntity login(String email) {
    UserEntity user = userMapper.login(email);
    return user;
  }

  @Override
  @Transactional
  public int join(UserEntity user) throws Exception {
    String userPassword = user.getPassword();
    String encodedUserPassword = passwordEncoder.encode(userPassword);
    user.setPassword(encodedUserPassword);

    //회원등록
    int result = userMapper.join(user);

    if (result > 0) {
      UserAuthEntity userAuthEntity = new UserAuthEntity();
      userAuthEntity.setUserEmail(user.getEmail());
      userAuthEntity.setAuth("ROLE_USER");
      result += userMapper.insertAuth(userAuthEntity);
    }
    return result;
  }

  @Override
  public int insertAuth(UserAuthEntity userAuthEntity) throws Exception {
    return 0;
  }


  @Override
  public void updateProfileImageUrl(UserRequestDTO user) throws Exception {
    userMapper.updateProfileImageUrl(user.getUser());
  }

  @Override
  public String uploadFile(MultipartFile file) throws Exception {
    // 파일 저장 로직 및 URL 생성
    String fileName = file.getOriginalFilename();
    Path filePath = Paths.get("uploads", fileName);
    Files.write(filePath, file.getBytes());
    // 파일 URL 반환 (예: "/uploads/filename")
    return "/uploads/" + fileName;
  }
}
