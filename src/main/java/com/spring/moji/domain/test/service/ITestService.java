package com.spring.moji.domain.test.service;

import java.util.List;

import com.spring.moji.domain.test.dto.request.TestRequestDTO;
import com.spring.moji.domain.test.dto.response.TestResponseDTO;

public interface ITestService {
	List<TestResponseDTO> getAllIds();
	void createTestUser(TestRequestDTO testRequestDTO);
}
