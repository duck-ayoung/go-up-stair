package com.duck.ayoung.goupstair.service;

import com.duck.ayoung.goupstair.domain.Group;
import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.repository.GroupRepository;
import com.duck.ayoung.goupstair.repository.MemberRepository;
import com.duck.ayoung.goupstair.web.GroupForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createGroup(GroupForm groupForm, Long memberId) {
        Group group = new Group(groupForm.getName(), groupForm.getIconImage(), groupForm.getDescription());
        Member member = memberRepository.findOne(memberId);
        groupRepository.save(group, member);
        return group.getId();
    }

    @Transactional
    public void joinGroup(Long groupId, Long memberId) {
        Group group = groupRepository.findOne(groupId);
        Member member = memberRepository.findOne(memberId);
        groupRepository.join(group, member);
    }

    public List<Member> findMember(Long groupId) {
        return groupRepository.findMember(groupId);
    }

}
