package com.spring.moji.mapper;

import com.spring.moji.dto.request.CommunityRequestDTO;
import com.spring.moji.dto.request.ImageUrlRequestDTO;
import com.spring.moji.dto.request.LocationRequestDTO;
import com.spring.moji.dto.request.PageInsertRequestDTO;
import com.spring.moji.entity.ImageUrl;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.dto.request.PageRequestDTO;
import com.spring.moji.entity.Page;

@Mapper
public interface PageMapper {
	List<Page> findByDuration(PageRequestDTO pageRequestDTO);
	List<Page> findPublicByDistance(CommunityRequestDTO communityRequestDTO);
	Page findRecentPage(PageRequestDTO pageRequestDTO);
	int deleteByPageId(Long pageId);
	void insertPage(PageInsertRequestDTO pageInsertRequestDTO);
}
