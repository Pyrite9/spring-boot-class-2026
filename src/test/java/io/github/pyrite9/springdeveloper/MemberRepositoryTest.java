package io.github.pyrite9.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블의 모든 레코드 가져오기")
    @Test
    void getAllMembers() {
        // given: @Sql

        // when
        List<Member> members = memberRepository.findAll(); // select * from member;

        // then
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블에서 id가 2인 레코드 가져오기")
    @Test
    void getMemberById2() {
        // given: @Sql

        // when
        Member member = memberRepository.findById(2L).get(); // select * from member where id = 2;

        // then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블에서 name이 'B'인 레코드 가져오기")
    @Test
    void getMemberByName() {
        // given: @Sql

        // when
        Member member = memberRepository.findByName("B").get(); // select * from member where name = 'B';

        // then
        assertThat(member.getId()).isEqualTo(2L);
    }

    @DisplayName("하나의 Member 객체를 DB에 저장하기")
    @Test
    void saveMember() {
        // given
        Member member = new Member("A", "a@domain.com");

        // when
        memberRepository.save(member);

        // then
        // identity counter is not rolled back between tests, so check name instead of id == 1
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getName()).isEqualTo("A");
    }

    @DisplayName("3개의 Member 객체를 DB에 저장하기")
    @Test
    void saveMembers() {
        // given
        List<Member> members = List.of(
                new Member("A1", "a1@domain.com"),
                new Member("A2", "a2@domain.com"),
                new Member("A3", "a3@domain.com"));

        // when
        memberRepository.saveAll(members);

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @DisplayName("id가 2인 레코드 삭제하기")
    @Test
    void deleteMemberById() {
        // given: @Sql

        // when
        memberRepository.deleteById(2L);

        // then
        Optional<Member> member = memberRepository.findById(2L);
        assertThat(member.isEmpty()).isTrue();
    }

    @Sql("/insert-members.sql")
    @DisplayName("모든 레코드 삭제하기")
    @Test
    void deleteAllMembers() {
        // given: @Sql

        // when
        memberRepository.deleteAll();

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }

    // update record via dirty-checking of persistence context
    @Sql("/insert-members.sql")
    @DisplayName("변경감지 기능을 이용해 id가 2인 레코드의 name을 '홍길동'으로 변경하기")
    @Test
    void updateMember() {
        // given
        Member member = memberRepository.findById(2L).get();

        // when
        member.setName("홍길동"); // no save() call

        // then
        member = memberRepository.findById(2L).get();
        assertThat(member.getName()).isEqualTo("홍길동");
    }
}
