package com.pae.server.assistant.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateCareerResDto(String description, LocalDate startDate, LocalDate endDate) {
}
