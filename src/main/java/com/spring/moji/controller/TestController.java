package com.spring.moji.controller;

import com.spring.moji.dto.request.TestRequestDTO;
import com.spring.moji.dto.response.TestResponseDTO;
import com.spring.moji.service.TestServiceImpl;
import com.spring.moji.util.S3Util;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    @Value("${kakao.api-key}")
    private String kakaoApiKey;
    private final TestServiceImpl testService;
    private final S3Util s3Util;

    @GetMapping("/diary/cover")
    public String coverDiary(Model model) {
        model.addAttribute("contentURL", "/WEB-INF/jsp/content/diary/cover-diary.jsp");
        return "diary/cover-diary-page";
    }

    @GetMapping("/diary/read")
    public String createDiary(Model model) {
        model.addAttribute("contentURL", "/WEB-INF/jsp/content/diary/read-diary.jsp");
        return "diary/read-diary-page";
    }

    @GetMapping("/diary/write")
    public String writeDiary(Model model) {
        model.addAttribute("contentURL", "/WEB-INF/jsp/content/diary/write-diary.jsp");
        return "diary/write-diary-page";
    }

    @GetMapping("/public")
    public String pulbic(Model model) {
        model.addAttribute("contentURL", "/WEB-INF/jsp/content/user/solo-profile.jsp");
        return "user/profile-page";
    }

    @GetMapping("/read")
    public String readTestUser(Model model) {
        List<TestResponseDTO> tests = testService.getAllIds();
        System.out.println(tests.toString());
        model.addAttribute("tests", tests);
        return "test/test-read-user";
    }

    @GetMapping("/create")
    public String showCreateTestUserForm() {
        return "test/test-create-user";
    }

    @PostMapping("/create")
    public String createTestUser(@ModelAttribute TestRequestDTO testRequestDTO) {
        testService.createTestUser(testRequestDTO);
        return "redirect:/test/read";
    }

    @GetMapping("/upload")
    public String showUploadImageForm() {
        return "test/test-upload-form";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String imageUrl = s3Util.uploadFile(file);
            model.addAttribute("imageUrl", imageUrl);

        } catch (Exception e) {
            model.addAttribute("message", "File upload failed");
        }
        return "test/test-read-image";
    }


    //커뮤니티 컴포넌트 제대로 뜨는가
    @GetMapping("/community")
    public String readCommunity(Model model) {
        model.addAttribute("contentURL", "/WEB-INF/jsp/content/community/community-diary.jsp");
        return "community/community-diary-page";
    }
}
