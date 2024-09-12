package com.spring.moji.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.spring.moji.entity.TestEntity;

@Mapper
public interface TestMapper {
	List<TestEntity> findAll();
	void createTestUser(TestEntity testEntity);
}
