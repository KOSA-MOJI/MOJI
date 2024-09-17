package com.spring.moji.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {
    private Long bookmarkId;
    private String bookmarkImage;
    private LocalDate createdAt;
}
