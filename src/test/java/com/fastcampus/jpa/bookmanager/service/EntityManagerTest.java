package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.UserRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Martin
 * @since 2021/05/13
 */
@SpringBootTest
public class EntityManagerTest {

    @Autowired
    private EntityManager em;

    // entityManager를 wrapping한 클래스인 JpaRepository 등등이 있지만,
    // 성능이슈나 지원하지 않는 기능은 직접 em으로 구현할 수 있다.

    @Autowired
    private UserRepository userRepository;
    @Test
    void entityManager(){
        System.out.println(em.createQuery("select u from User u")
                .getResultList());
    }

    @Test
    void cacheFindTest(){
        System.out.println(userRepository.findById(1L).get());
        System.out.println(userRepository.findById(1L).get());
        System.out.println(userRepository.findById(1L).get());

        // 결과는 3개가 output되지만 select 쿼리는 한번만 날린다.
        // 같은 트랜잭션 내에서 한번 select가 되면 영속성 컨텍스트에 등록이 된다
        // 다시 같은 쿼리가 날라오면 db에 쿼리를 조회하지 않고 select없이 처리할 수 있다.

        // 1차 캐시는 Map<id,Entity> 방식으로 저장된다.


       /* System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));*/
    }

}
