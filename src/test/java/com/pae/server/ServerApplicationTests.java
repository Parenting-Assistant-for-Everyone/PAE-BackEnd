package com.pae.server;

import com.pae.server.member.domain.Member;
import com.pae.server.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {
	@Autowired
	MemberRepository memberRepository;

	@Test
	void contextLoads() {
	}
	@Test
	void test(){
		Member member = memberRepository.findById(1L).get();
		System.out.println(member.getAddress());
	}

}
