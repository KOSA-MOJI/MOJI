package com.spring.moji.service;

import com.spring.moji.dto.request.UserRequestDTO;
import com.spring.moji.entity.UserAuthEntity;
import com.spring.moji.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  public UserEntity login(String username);

  public int join(UserEntity user) throws Exception;

  public int insertAuth(UserAuthEntity userAuthEntity) throws Exception;

  public void updateProfileImageUrl(UserRequestDTO user) throws Exception;

  public String uploadFile(MultipartFile file) throws Exception;
}
