package basic.lock;

import static javax.persistence.LockModeType.OPTIMISTIC;
import static javax.persistence.LockModeType.OPTIMISTIC_FORCE_INCREMENT;
import static javax.persistence.LockModeType.PESSIMISTIC_FORCE_INCREMENT;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;
import static org.assertj.core.api.Assertions.assertThat;

import basic.lock.safe.Member;
import basic.lock.safe.MemberService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import javax.persistence.LockModeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LockTest {

  private static final int THREAD_COUNT = 100;

  @Autowired
  MemberService service;

  ExecutorService executorService;

  Member member;

  @BeforeEach
  void setUp() {
    executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    member = service.save();
  }

  @Test
  @DisplayName("Optimistic Lock")
  void test_optimistic() throws InterruptedException {
    runThreads(OPTIMISTIC);

    Member retrievedEntity = service.findMember(member.getId());
    assertThat(retrievedEntity.getAge()).isNotEqualTo(THREAD_COUNT);
  }

  @Test
  @DisplayName("Optimistic Force Increment Lock")
  void test_optimistic_force_increment() throws InterruptedException {
    runThreads(OPTIMISTIC_FORCE_INCREMENT);

    Member retrievedEntity = service.findMember(member.getId());
    assertThat(retrievedEntity.getAge()).isNotEqualTo(THREAD_COUNT);
  }

  @Test
  @DisplayName("Pessimistic Lock")
  void test_pessimistic_write() throws InterruptedException {
    runThreads(PESSIMISTIC_WRITE);

    Member retrievedEntity = service.findMember(member.getId());
    assertThat(retrievedEntity.getAge()).isEqualTo(THREAD_COUNT);
  }

  @Test
  @DisplayName("Pessimistic Force Increment Lock")
  void test_pessimistic_force_increment() throws InterruptedException {
    runThreads(PESSIMISTIC_FORCE_INCREMENT);

    Member retrievedEntity = service.findMember(member.getId());
    assertThat(retrievedEntity.getAge()).isEqualTo(THREAD_COUNT);
  }

  private void runThreads(LockModeType lockModeType) throws InterruptedException {
    executorService.invokeAll(
        Stream.generate(() -> (Callable<Object>) () ->
                service.findMemberWithLock(member.getId(), lockModeType))
            .limit(THREAD_COUNT)
            .toList()
    );
  }
}