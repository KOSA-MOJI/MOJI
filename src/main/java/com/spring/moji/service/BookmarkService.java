package com.spring.moji.service;

import com.spring.moji.entity.Bookmark;

public interface BookmarkService {
    Bookmark findByBookmarkId(Long bookmarkId);
}
