package com.spring.moji.controller;

import java.util.ArrayList;
import java.util.List;

import com.spring.moji.dto.request.CommunityRequestDTO;
import com.spring.moji.dto.request.CommunityScrapRequestDTO;
import com.spring.moji.dto.response.CommunityResponseDTO;
import com.spring.moji.entity.Page;
import com.spring.moji.service.CommunityServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityRestController {

    private final CommunityServiceImpl communityService;

    @RequestMapping()
    public List<CommunityResponseDTO> getCommunityUnderList(@ModelAttribute CommunityRequestDTO communityRequestDTO) {
        return communityService.getCommunityUnderList(communityRequestDTO);
    }
	@RequestMapping("/page/{pageId}")
	public Page getCommunityUnderListPage(@PathVariable Long pageId){
		return communityService.getPublicPage(pageId);
	}
	@PostMapping("/scrap")
	public boolean addScrap(@RequestParam String email, @RequestParam Long pageId) {
		ArrayList<Long> pageIds = new ArrayList<>();
		pageIds.add(pageId);
		CommunityScrapRequestDTO communityScrapRequestDTO = CommunityScrapRequestDTO.builder().email(email).pageIds(pageIds).build();
		return communityService.addScrap(communityScrapRequestDTO);
	}
	@DeleteMapping("/scrap")
	public boolean removeScrap(@RequestParam String email, @RequestParam Long pageId) {
		ArrayList<Long> pageIds = new ArrayList<>();
		pageIds.add(pageId);
		CommunityScrapRequestDTO communityScrapRequestDTO = CommunityScrapRequestDTO.builder().email(email).pageIds(pageIds).build();
		return communityService.removeScrap(communityScrapRequestDTO);
	}
}
