package com.spring.moji.mapper;

import com.spring.moji.entity.Bookmark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookmarkMapper {
    List<Bookmark> findAll();
    Bookmark findByBookmarkId(Long bookmarkId);
}
