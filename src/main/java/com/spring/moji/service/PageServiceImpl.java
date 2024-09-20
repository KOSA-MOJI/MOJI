package com.spring.moji.service;

import com.spring.moji.dto.request.ImageUrlRequestDTO;
import com.spring.moji.dto.request.LocationRequestDTO;
import com.spring.moji.dto.request.PageInsertRequestDTO;
import com.spring.moji.entity.ImageUrl;
import com.spring.moji.entity.Location;
import com.spring.moji.mapper.ImageUrlMapper;
import com.spring.moji.mapper.LocationMapper;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.moji.dto.request.PageRequestDTO;
import com.spring.moji.entity.Page;
import com.spring.moji.mapper.PageMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {
	private final PageMapper pageMapper;
	private final LocationMapper locationMapper;
	private final ImageUrlMapper imageUrlMapper;

	@Override
	public List<Page> fetchDiaryPages(Long diaryId, LocalDate startDate, LocalDate endDate) {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().diaryId(diaryId).startDate(startDate).endDate(endDate).build();
		List<Page> pages = pageMapper.findByDuration(pageRequestDTO);
		return pages;
	}

	@Override
	public Page findRecentPage(Long diaryId) {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().diaryId(diaryId).build();
		Page page = pageMapper.findRecentPage(pageRequestDTO);
		return page;
	}

	@Override
	public void deleteByPageId(Long pageId) {
		int rowsAffected = pageMapper.deleteByPageId(pageId);
		if (rowsAffected == 0) {
			throw new RuntimeException("No page found with the given ID");
		}
	}
	@Transactional
	@Override
	public void addPageWithDetails(PageInsertRequestDTO pageInsertRequestDTO) {
		// 페이지 삽입 및 자동 생성된 pageId 획득
		System.out.println(pageInsertRequestDTO.toString());
		pageMapper.insertPage(pageInsertRequestDTO);
		System.out.println(pageInsertRequestDTO.toString());
		Long pageId = pageInsertRequestDTO.getPageId();  // 자동 생성된 pageId를 여기서 가져옵니다.

		// 각 지도 위치에 대해 삽입
		for (Location location : pageInsertRequestDTO.getLocations()) {
			// 각 위치에 pageId를 할당 후 삽입
			LocationRequestDTO locationRequestDTO = LocationRequestDTO.builder()
					.pageId(pageId)  // 여기서 pageId를 설정
					.address(location.getAddress())
					.latitude(location.getLatitude())
					.longitude(location.getLongitude())
					.build();
			System.out.println(locationRequestDTO.toString());
			locationMapper.insertLocation(locationRequestDTO);
			Long locationId = locationRequestDTO.getLocationId();  // 자동 생성된 locationId
			// 각 지도 위치마다 이미지 URL 삽입
			for (ImageUrl imageUrl : location.getImageUrls()) {
				ImageUrlRequestDTO imageUrlRequestDTO = ImageUrlRequestDTO.builder()
						.locationId(locationId)  // locationId를 사용
						.mapImage(imageUrl.getMapImage())
						.build();
				System.out.println(imageUrlRequestDTO.toString());
				imageUrlMapper.insertImageUrl(imageUrlRequestDTO);
			}
		}
	}

}

