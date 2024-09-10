package com.spring.moji.domain.test.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestEntity {
	private String id;
	private String password;

	@Builder
	public TestEntity(String id, String password) {
		this.id = id;
		this.password = password;
	}
}
