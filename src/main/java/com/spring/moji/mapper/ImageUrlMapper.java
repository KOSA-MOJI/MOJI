package com.spring.moji.mapper;

import com.spring.moji.dto.request.ImageUrlInsertRequestDTO;
import com.spring.moji.dto.request.ImageUrlRequestDTO;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.entity.ImageUrl;

@Mapper
public interface ImageUrlMapper {
	List<ImageUrl> findAllByLocationId(Long locationId);
	List<ImageUrl> findAllByPageId(Long imageId);
	void insertImageUrl(ImageUrlInsertRequestDTO imageUrlInsertRequestDTO);
}
