package com.spring.moji.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityRequestDTO {
    private String email;
    private Double longitude;
    private Double latitude;
    private Long radius;
    private Long offset;
    private Long limit;
}
