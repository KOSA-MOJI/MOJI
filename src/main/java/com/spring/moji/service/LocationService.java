package com.spring.moji.service;

import java.util.List;

import com.spring.moji.entity.Location;

public interface LocationService {
	List<Location> findAllByPageId(Long pageId);
}
