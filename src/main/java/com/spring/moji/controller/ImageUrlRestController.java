package com.spring.moji.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.moji.entity.ImageUrl;
import com.spring.moji.service.ImageUrlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/imageURL")
public class ImageUrlRestController {
	private final ImageUrlService imageUrlService;

	@GetMapping("/{locationId}")
	public List<ImageUrl> findAllByLocationId(@PathVariable Long locationId) {
		return imageUrlService.findAllByLocationId(locationId);
	}
}
