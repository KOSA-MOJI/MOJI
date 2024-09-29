package com.spring.moji.dto.request;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BreakupRequestDTO {

  private String receiverEmail;
}
