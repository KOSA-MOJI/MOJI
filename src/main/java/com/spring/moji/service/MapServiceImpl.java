package com.spring.moji.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.moji.dto.request.MapRequestDTO;
import com.spring.moji.dto.request.PageRequestDTO;
import com.spring.moji.entity.Location;
import com.spring.moji.entity.Page;
import com.spring.moji.mapper.LocationMapper;
import com.spring.moji.mapper.PageMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
	private final PageMapper pageMapper;
	private final LocationMapper locationMapper;

	@Override
	public List<Location> getDiaryLocations(Long coupleId) {
		List<Location> locations = locationMapper.findAllFirstLocation(coupleId);
		return locations;
	}

	@Override
	public List<Location> getMyScrapLocations(String email) {
		return locationMapper.findScrapedLocations(email);
	}

	@Override
	public List<Location> getPartnerScrapLocations(MapRequestDTO mapRequestDTO) {
		return locationMapper.findPartnerScrapedLocations(mapRequestDTO);
	}

	@Override
	public Page getPage(Long pageId) {
		return null;
	}
}
