package com.spring.moji.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.moji.entity.Location;
import com.spring.moji.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationRestController {
	private final LocationService locationService;

	// TODO : 로케이션 값을 여러개 들고올 때, 정렬기준을 무엇으로 할 것인가
	// TODO : 삭제, 또는 수정 시 앞선 문제에서도 로직이 필요할듯 하다..
	@GetMapping("/{pageId}")
	public List<Location> findAllByPageId(@PathVariable Long pageId) {
		return locationService.findAllByPageId(pageId);
	}
}
