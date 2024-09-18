package com.spring.moji.service;

import com.spring.moji.entity.Bookmark;

import java.util.List;

public interface BookmarkService {
    Bookmark findByBookmarkId(Long bookmarkId);
    List<Bookmark> findAll();
}
