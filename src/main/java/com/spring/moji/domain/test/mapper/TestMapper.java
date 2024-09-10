package com.spring.moji.domain.test.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.spring.moji.domain.test.entity.TestEntity;

@Mapper
public interface TestMapper {
	List<TestEntity> findAll();
	void createTestUser(TestEntity testEntity);
}
