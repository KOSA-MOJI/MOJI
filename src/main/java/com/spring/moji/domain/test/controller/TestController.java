package com.spring.moji.domain.test.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.spring.moji.domain.test.dto.request.TestRequestDTO;
import com.spring.moji.domain.test.dto.response.TestResponseDTO;
import com.spring.moji.domain.test.service.TestService;
import com.spring.moji.util.S3Util;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
	private final TestService testService;
	private final S3Util s3Util;

	@GetMapping
	public String index() {
		return "user/main-page";
	}

	@GetMapping("/read")
	public String readTestUser(Model model) {
		List<TestResponseDTO> tests = testService.getAllIds();
		model.addAttribute("tests", tests);
		return "test/test-read-user";
	}

	@GetMapping("/create")
	public String showCreateTestUserForm() {
		return "test/test-create-user";
	}

	@PostMapping("/create")
	public String createTestUser(@ModelAttribute TestRequestDTO	testRequestDTO) {
		System.out.println(testRequestDTO);
		testService.createTestUser(testRequestDTO);
		return "redirect:/test/read";
	}

	@GetMapping("/upload")
	public String showUploadImageForm() {
		return "test/test-upload-form";
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file")MultipartFile file, Model model) {
		try {
			String imageUrl = s3Util.uploadFile(file);
			System.out.println(imageUrl);
			model.addAttribute("imageUrl", imageUrl);
		} catch (Exception e) {
			model.addAttribute("message", "File upload failed");
		}
		return "test/test-read-image";
	}
}
