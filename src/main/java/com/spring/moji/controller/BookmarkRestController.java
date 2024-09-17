package com.spring.moji.controller;


import com.spring.moji.entity.Bookmark;
import com.spring.moji.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkRestController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public List<Bookmark> getAllBookmarks() {
        return bookmarkService.findAll();
    }

    @GetMapping("/{bookmarkId}")
    public Bookmark findByBookmarkId(@PathVariable Long bookmarkId) {
        return bookmarkService.findByBookmarkId(bookmarkId);
    }

}
