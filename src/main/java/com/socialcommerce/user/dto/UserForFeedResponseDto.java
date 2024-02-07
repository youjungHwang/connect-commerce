package com.socialcommerce.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserForFeedResponseDto {
    private Long id;
    private String email;
    private String username;
    private String profileImage;
    private String greeting;
    private Boolean isInfluencer;
}
