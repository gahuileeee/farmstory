package kr.co.farmstory.repository;

import kr.co.farmstory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // 이름과 이메일 찾기 (아이디 찾기용)
    public Optional<User> findUserByNameAndEmail(String name, String email);

    // 아이디와 이메일 찾기 (비밀번호 찾기용)
    public Optional<User> findUserByUidAndEmail(String uid, String email);

}