package com.spring.moji.domain.test.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.moji.domain.test.dto.request.TestRequestDTO;
import com.spring.moji.domain.test.dto.response.TestResponseDTO;
import com.spring.moji.domain.test.entity.TestEntity;
import com.spring.moji.domain.test.mapper.TestMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TestService implements ITestService {

	private final TestMapper testMapper;

	@Override
	public List<TestResponseDTO> getAllIds() {
		return testMapper.findAll().stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	@Override
	public void createTestUser(TestRequestDTO testRequestDTO) {
		TestEntity testEntity = TestEntity.builder().id(testRequestDTO.getId()).password(testRequestDTO.getPassword()).build();
		testMapper.createTestUser(testEntity);
	}

	private TestResponseDTO convertToDTO(TestEntity test) {
		return TestResponseDTO.builder().id(test.getId())
			.build();
	}
}
