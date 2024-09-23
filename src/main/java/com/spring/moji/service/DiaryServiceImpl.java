package com.spring.moji.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.moji.dto.request.DiaryRequestDTO;
import com.spring.moji.dto.request.ImageUrlInsertRequestDTO;
import com.spring.moji.dto.request.LocationInsertRequestDTO;
import com.spring.moji.dto.request.PageInsertRequestDTO;
import com.spring.moji.dto.request.PageRequestDTO;
import com.spring.moji.entity.Diary;
import com.spring.moji.entity.ImageUrl;
import com.spring.moji.entity.Page;
import com.spring.moji.entity.Template;
import com.spring.moji.mapper.DiaryMapper;
import com.spring.moji.mapper.ImageUrlMapper;
import com.spring.moji.mapper.LocationMapper;
import com.spring.moji.mapper.PageMapper;
import com.spring.moji.mapper.TemplateMapper;
import com.spring.moji.util.S3Util;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

	private final DiaryMapper diaryMapper;
	private final PageMapper pageMapper;
	private final TemplateMapper templateMapper;
	private final ImageUrlMapper imageUrlMapper;
	private final LocationMapper locationMapper;
	private final S3Util s3Util;

	@Override
	public Diary findByCoupleId(Long coupleId) {
		return diaryMapper.findByCoupleId(coupleId);
	}

	@Override
	public List<Page> fetchDiaryPages(Long diaryId, LocalDate startDate, LocalDate endDate) {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().diaryId(diaryId).startDate(startDate).endDate(endDate).build();
		return pageMapper.findByDuration(pageRequestDTO);
	}

	@Override
	@Transactional
	public String updateCoverImage(Long diaryId, MultipartFile diaryCoverImage) throws IOException {
		// TODO : 기존 S3내 이미지 삭제 로직 추가
		String imageURL = s3Util.uploadFile(diaryCoverImage);
		DiaryRequestDTO diaryRequestDTO = DiaryRequestDTO.builder().diaryId(diaryId).coverImage(imageURL).build();
		diaryMapper.updateCoverImage(diaryRequestDTO);
		return imageURL;
	}

	@Override
	public List<Template> findAllTemplates() {
		return templateMapper.findAll();
	}

	@Override
	@Transactional
	public void setPagePublicStatus(Long pageId, boolean publicStatus) {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().pageId(pageId).publicStatus(publicStatus?'y':'n').build();
		pageMapper.updatePublicStatusByPageId(pageRequestDTO);
	}

	@Override
	public String preSigningImage(MultipartFile image){
		try {
			return s3Util.uploadFile(image);
		}catch (Exception e) {
			return "uploadFailed";
		}
	}

	@Override
	public void deletePreSignedImage(String imageUrl){
		try {
			s3Util.deleteFile(imageUrl);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void createPage(PageInsertRequestDTO pageInsertRequestDTO) {
		pageMapper.insertPage(pageInsertRequestDTO);
		Long pageId = pageInsertRequestDTO.getPageId();
		for (LocationInsertRequestDTO locationInsertRequestDTO : pageInsertRequestDTO.getLocations()) {
			locationInsertRequestDTO.setPageId(pageId);
			locationMapper.insertLocation(locationInsertRequestDTO);
			Long locationId = locationInsertRequestDTO.getLocationId();
			for (ImageUrlInsertRequestDTO imageUrlInsertRequestDTO : locationInsertRequestDTO.getImageUrls()) {
				imageUrlInsertRequestDTO.setLocationId(locationId);
				imageUrlMapper.insertImageUrl(imageUrlInsertRequestDTO);
			}
		}
	}
	@Override
	@Transactional
	public void deletePageById(Long pageId) {
		List<ImageUrl> imageUrls = imageUrlMapper.findAllByPageId(pageId);
		for (ImageUrl imageUrl : imageUrls) {
			System.out.println(imageUrl.getMapImage());
			try {
				s3Util.deleteFile(imageUrl.getMapImage());
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		pageMapper.deleteByPageId(pageId);
	}
}
