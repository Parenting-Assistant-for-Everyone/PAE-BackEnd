package com.pae.server.assistant.repository;

import com.pae.server.assistant.domain.Assistant;
import com.pae.server.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantRepository extends JpaRepository<Assistant, Long> {
}
