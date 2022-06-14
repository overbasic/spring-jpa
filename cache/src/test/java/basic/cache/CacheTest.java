package basic.cache;

import static org.assertj.core.api.Assertions.assertThat;

import basic.cache.entity.Member;
import basic.cache.entity.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  @DisplayName("캐시")
  void testCache(){
    Member member = memberRepository.save(new Member(2L));

    Member member1 = memberRepository.findById(1L).get();
    System.out.println("member1 = " + member1);

    Member member2 = memberRepository.findById(1L).get();
    System.out.println("member2 = " + member2);

    assertThat(member1).isNotEqualTo(member2);
  }

}