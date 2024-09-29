package com.spring.moji.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserInfoEditRequestDTO implements Serializable {

  @JsonIgnore
  private MultipartFile file;
  private String email;
}
