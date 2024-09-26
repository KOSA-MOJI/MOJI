package com.spring.moji.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.moji.dto.request.MapRequestDTO;
import com.spring.moji.entity.Location;
import com.spring.moji.entity.Page;
import com.spring.moji.service.MapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/map")
public class MapRestController {
	private final MapService mapService;

	// 커플 다이어리로 가져오기
	@GetMapping("/diary/{coupleId}")
	public List<Location> getDiaryLocations(@PathVariable Long coupleId) {
		return mapService.getDiaryLocations(coupleId);
	}

	// 개인 스크랩으로 가져오기
	@GetMapping("/scrap/mine")
	public List<Location> scrapMine(@RequestParam String email){
		return mapService.getMyScrapLocations(email);
	}

	// 상대 스크랩으로 가져오기
	@GetMapping("/scrap/partner")
	public List<Location> scrapPartner(@ModelAttribute MapRequestDTO mapRequestDTO) {
		return mapService.getPartnerScrapLocations(mapRequestDTO);
	}

	// 단건 페이지 조회
	@GetMapping("/page/{pageId}")
	public Page getPage(@PathVariable Long pageId) {
		return mapService.getPage(pageId);
	}
}
