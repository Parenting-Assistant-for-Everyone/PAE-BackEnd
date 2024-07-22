package com.pae.server.member.repository;

import com.pae.server.assistant.domain.MatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchHistory,Long> {
    boolean existsByMemberId(Long memberId);
}
