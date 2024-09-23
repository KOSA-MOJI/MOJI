package com.spring.moji.entity;


import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Request {

  private Long requestId;
  private String requestEmail;
  private String receiverEmail;
  private LocalDate createdAt;

  @Builder
  public Request(Long requestId, String requestEmail, String receiverEmail, LocalDate createdAt) {
    this.requestId = requestId;
    this.requestEmail = requestEmail;
    this.receiverEmail = receiverEmail;
    this.createdAt = createdAt;

  }
}
