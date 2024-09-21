package com.spring.moji.service;

import java.util.List;

import com.spring.moji.dto.request.CommunityRequestDTO;
import com.spring.moji.dto.response.CommunityResponseDTO;


public interface CommunityService {
	List<CommunityResponseDTO> getCommunityUnderList(CommunityRequestDTO communityRequestDTO);
}
