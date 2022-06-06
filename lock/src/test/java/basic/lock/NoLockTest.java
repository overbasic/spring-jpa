package basic.lock;

import static org.assertj.core.api.Assertions.assertThat;

import basic.lock.unsafe.NoLock;
import basic.lock.unsafe.NoLockDataRepository;
import basic.lock.unsafe.NoLockService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoLockTest {

  @Autowired
  NoLockService service;

  @Autowired
  NoLockDataRepository repository;

  @Test
  @DisplayName("락을 걸지 않으면 동시성 문제가 발생한다.")
  void test_no_lock() throws InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(100);

    NoLock noLock = this.service.save();

    service.invokeAll(
        Stream.generate(() -> (Callable<Object>) () -> this.service.find(noLock.getId()))
            .limit(100)
            .toList()
    );

    NoLock retrievedNoLock = repository.findById(noLock.getId()).orElseThrow();
    assertThat(retrievedNoLock.getCount()).isNotEqualTo(100);
  }
}