package com.spring.moji.service;

import java.util.List;

import com.spring.moji.dto.request.CommunityRequestDTO;
import com.spring.moji.dto.request.CommunityScrapRequestDTO;
import com.spring.moji.dto.response.CommunityResponseDTO;
import com.spring.moji.entity.Page;

public interface CommunityService {
	List<CommunityResponseDTO> getCommunityUnderList(CommunityRequestDTO communityRequestDTO);
	Page getPublicPage(Long pageId);
	void addScrap(CommunityScrapRequestDTO communityScrapRequestDTO);
	void removeScrap(CommunityScrapRequestDTO communityScrapRequestDTO);
}
