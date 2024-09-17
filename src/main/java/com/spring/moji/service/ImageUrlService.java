package com.spring.moji.service;

import java.util.List;

import com.spring.moji.entity.ImageUrl;

public interface ImageUrlService {
	List<ImageUrl> findAllByLocationId(Long locationId);
}
