package com.pae.server.member.dto.request;

import com.pae.server.assistant.domain.enums.MatchingStatus;

import java.time.LocalDate;

public record MatchingReqDto(Long childAssistantId, Long memberId, LocalDate endDate, MatchingStatus matchingStatus) {
}
