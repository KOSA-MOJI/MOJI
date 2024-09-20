package com.spring.moji.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityRequestDTO {

    private double longitude;
    private double latitude;
    private int radius;
    private int offset;
    private int limit;
}
