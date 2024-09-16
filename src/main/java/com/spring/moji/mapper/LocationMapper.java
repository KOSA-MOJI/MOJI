package com.spring.moji.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.entity.Location;

@Mapper
public interface LocationMapper {
	List<Location> findAllByPageId(Long pageId);
}
