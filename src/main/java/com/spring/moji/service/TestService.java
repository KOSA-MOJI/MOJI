package com.spring.moji.service;

import java.util.List;

import com.spring.moji.dto.request.TestRequestDTO;
import com.spring.moji.dto.response.TestResponseDTO;

public interface TestService {
	List<TestResponseDTO> getAllIds();
	void createTestUser(TestRequestDTO testRequestDTO);
}
