package com.spring.moji.entity;


import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Request {

  private Long RequestId;
  private String RequestEmail;
  private String ReceiverEmail;
  private LocalDate createdAt;

  @Builder
  public Request(Long RequestId, String RequestEmail, String ReceiverEmail, LocalDate createdAt) {
    this.RequestId = RequestId;
    this.RequestEmail = RequestEmail;
    this.ReceiverEmail = ReceiverEmail;
    this.createdAt = createdAt;

  }
}
