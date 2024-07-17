package com.pae.server.member.repository;

import com.pae.server.member.domain.ChildInformation;
import com.pae.server.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildInformationRepository extends JpaRepository<ChildInformation, Long> {
}
