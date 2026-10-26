package io.github.pyrite9.springdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // findAll()    : select * from member;
    // findById()   : select * from member where id = ?
    // deleteAll()  : delete from member;
    // deleteById() : delete from member where id = ?
    // save()       : insert into member(name, email) values (?, ?) / update member set ... where id = ?

    // query method: derived from method name
    // select * from member where name = ?
    Optional<Member> findByName(String name);

    // select * from member where email = ?
    // Optional<Member> findByEmail(String email);

    // select * from member where name = ? and email = ?
    // Optional<Member> findByNameAndEmail(String name, String email);
}
