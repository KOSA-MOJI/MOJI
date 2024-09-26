package com.spring.moji.service;

import java.util.List;

import com.spring.moji.entity.Location;
import com.spring.moji.entity.Page;

public interface MapService {
	public List<Location> getDiaryLocations(Long coupleId);
	public List<Location> getMyScrapLocations();
	public List<Location> getPartnerScrapLocations();
}
