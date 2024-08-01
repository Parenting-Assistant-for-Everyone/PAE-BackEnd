package com.pae.server.board.dto.response;

import com.pae.server.assistant.domain.Career;
import com.pae.server.assistant.domain.enums.AssistantStatus;
import com.pae.server.assistant.domain.enums.CertificationStatus;
import com.pae.server.assistant.dto.response.CreateCareerListResDto;
import lombok.Builder;

import java.util.List;

@Builder
public record AssistantProfileResDto(String name, String introduce, CertificationStatus certificationStatus,
                                     AssistantStatus assistantStatus,String precaution, String profileUrl, CreateCareerListResDto careerList) {
}
