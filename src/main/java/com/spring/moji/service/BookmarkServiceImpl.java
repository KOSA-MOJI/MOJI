package com.spring.moji.service;

import com.spring.moji.entity.Bookmark;
import com.spring.moji.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkMapper bookmarkMapper;

    @Override
    public Bookmark findByBookmarkId(Long bookmarkId){
        return bookmarkMapper.findByBookmarkId(bookmarkId);
    }

    @Override
    public List<Bookmark> findAll(){
        return bookmarkMapper.findAll();
    }
}
