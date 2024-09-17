package com.spring.moji.controller;


import com.spring.moji.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkRestController {

    private final BookmarkService bookmarkService;


}
