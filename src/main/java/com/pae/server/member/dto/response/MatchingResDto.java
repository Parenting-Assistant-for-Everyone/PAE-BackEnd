package com.pae.server.member.dto.response;

import com.pae.server.assistant.domain.Assistant;
import com.pae.server.assistant.domain.enums.MatchingStatus;
import com.pae.server.member.domain.Member;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record MatchingResDto(String memberName, String assistantName, LocalDate endDate, Integer remainingDays,
                             MatchingStatus matchingStatus) {
}
