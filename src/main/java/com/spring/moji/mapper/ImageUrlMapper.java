package com.spring.moji.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.entity.ImageUrl;

@Mapper
public interface ImageUrlMapper {
	List<ImageUrl> findAllByLocationId(Long locationId);
}
