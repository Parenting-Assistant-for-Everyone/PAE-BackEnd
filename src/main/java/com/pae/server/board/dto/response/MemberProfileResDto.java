package com.pae.server.board.dto.response;

import com.pae.server.image.domain.ImageData;
import com.pae.server.member.domain.enums.CCTV;
import com.pae.server.member.domain.enums.Gender;
import lombok.Builder;

@Builder
public record MemberProfileResDto(String nickName, Gender gender, String profileUrl, CCTV cctv, String precautions, String introduce) {
}
