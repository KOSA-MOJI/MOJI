package com.spring.moji.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.moji.dto.request.CommunityRequestDTO;
import com.spring.moji.dto.request.CommunityScrapRequestDTO;
import com.spring.moji.dto.response.CommunityResponseDTO;
import com.spring.moji.entity.Location;
import com.spring.moji.entity.Page;
import com.spring.moji.entity.Scrap;
import com.spring.moji.mapper.PageMapper;
import com.spring.moji.mapper.ScrapMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
	private final PageMapper pageMapper;
	private final ScrapMapper scrapMapper;

	@Override
	public List<CommunityResponseDTO> getCommunityUnderList(CommunityRequestDTO communityRequestDTO) {
		List<Page> pages = pageMapper.findPublicByDistance(communityRequestDTO);

		List<Long> pageIds = pages.stream().map(Page::getPageId).toList();
		CommunityScrapRequestDTO communityScrapRequestDTO = CommunityScrapRequestDTO.builder().email(communityRequestDTO.getEmail()).pageIds(pageIds).build();

		List<Scrap> scraps = scrapMapper.findAllByPageIds(communityScrapRequestDTO);

		List<CommunityResponseDTO> communityResponseDTOs = new ArrayList<>();
		for(Page page : pages) {
			String imageUrl = null;
			if (page.getLocations() != null && !page.getLocations().isEmpty()) {
				Location location = page.getLocations().getFirst();
				if (location.getImageUrls() != null && !location.getImageUrls().isEmpty()) {
					imageUrl = location.getImageUrls().getFirst().getMapImage();
				}
			}
			communityResponseDTOs.add(
				CommunityResponseDTO.builder()
					.pageId(page.getPageId())
					.imageUrl(imageUrl)
					.isScrapped(scraps.stream().anyMatch(scrap ->scrap.getPageId().equals(page.getPageId())))
					.build());
		}
		return communityResponseDTOs;
	}

	@Override
	public Page getPublicPage(Long pageId) {
		return pageMapper.findByPageId(pageId);
	}

	@Override
	@Transactional
	public boolean addScrap(CommunityScrapRequestDTO communityScrapRequestDTO) {
		try{
			scrapMapper.createScrap(communityScrapRequestDTO);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean removeScrap(CommunityScrapRequestDTO communityScrapRequestDTO) {
		try{
			scrapMapper.removeScrap(communityScrapRequestDTO);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

}
