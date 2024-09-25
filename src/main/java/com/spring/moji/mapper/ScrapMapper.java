package com.spring.moji.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.dto.request.CommunityScrapRequestDTO;
import com.spring.moji.entity.Scrap;

@Mapper
public interface ScrapMapper {
	List<Scrap> findAllByPageIds(CommunityScrapRequestDTO communityScrapRequestDTO);
	List<Scrap> findAllByPageId(Long pageId);
	void createScrap(CommunityScrapRequestDTO communityScrapRequestDTO);
	void removeScrap(CommunityScrapRequestDTO communityScrapRequestDTO);

}
