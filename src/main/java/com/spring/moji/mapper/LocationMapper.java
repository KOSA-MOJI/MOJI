package com.spring.moji.mapper;

import com.spring.moji.dto.request.LocationInsertRequestDTO;
import com.spring.moji.dto.request.LocationRequestDTO;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.dto.request.MapRequestDTO;
import com.spring.moji.entity.Location;

@Mapper
public interface LocationMapper {
	List<Location> findAllByPageId(Long pageId);
	void insertLocation(LocationInsertRequestDTO locationInsertRequestDTO);
	List<Location> findAllFirstLocation(Long coupleId);
	List<Location> findScrapedLocations(String email);
	List<Location> findPartnerScrapedLocations(MapRequestDTO mapRequestDTO);
}
