package com.spring.moji.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.moji.entity.Location;
import com.spring.moji.mapper.LocationMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
	private final LocationMapper locationMapper;

	@Override
	public List<Location> findAllByPageId(Long pageId) {
		return locationMapper.findAllByPageId(pageId);
	}
}
