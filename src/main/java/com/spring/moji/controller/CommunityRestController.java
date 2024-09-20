package com.spring.moji.controller;

import com.spring.moji.service.PageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityRestController {

    private final PageServiceImpl pageService;


}
