package com.spring.moji.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.moji.entity.ImageUrl;
import com.spring.moji.mapper.ImageUrlMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageUrlServiceImpl implements ImageUrlService {
	private final ImageUrlMapper imageUrlMapper;

	@Override
	public List<ImageUrl> findAllByLocationId(Long locationId) {
		return imageUrlMapper.findAllByLocationId(locationId);
	}
}
