package com.pae.server.member.service;

import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.pae.server.member.converter.ChildInformationConverter;
import com.pae.server.member.domain.ChildInformation;
import com.pae.server.member.domain.Member;
import com.pae.server.member.dto.request.CreateChildInformationReqDto;
import com.pae.server.member.repository.ChildInformationRepository;
import com.pae.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildInformationServiceImpl implements ChildInformationService{
    private final ChildInformationRepository childInformationRepository;
    private final MemberRepository memberRepository;
    @Override
    public ChildInformation createChildInformation(CreateChildInformationReqDto dto) {
        if(!memberRepository.existsById(dto.memberId())){
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND);
        }
        else{
            ChildInformation childInformation = childInformationRepository.save(ChildInformationConverter.createChildInformation(dto));
            Member member = memberRepository.findById(dto.memberId()).get();
            childInformation.setMember(member);
            return childInformation;

        }

    }

    @Override
    public ChildInformation modifyChildInformation(Long id,CreateChildInformationReqDto dto) {
        if(!childInformationRepository.existsById(id)){
            throw new CustomException(CustomResponseStatus.CHILD_INFORMATION_NOT_FOUND);
        }
        else{
            ChildInformation childInformation = childInformationRepository.findById(id).get();
            childInformation.update(dto.name(), dto.age(), dto.gender(),dto.description());
            return childInformationRepository.save(childInformation);
        }
    }

    @Override
    public ChildInformation deleteChildInformation(Long id) {
        if(!childInformationRepository.existsById(id)){
            throw new CustomException(CustomResponseStatus.CHILD_INFORMATION_NOT_FOUND);
        }
        else{
            ChildInformation childInformation = childInformationRepository.findById(id).get();
            childInformationRepository.delete(childInformation);
            return childInformation;
        }
    }

    @Override
    public List<ChildInformation> searchChildInformation(Long id) {
        return null;
    }
}
