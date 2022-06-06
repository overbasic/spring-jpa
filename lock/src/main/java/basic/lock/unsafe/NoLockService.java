package basic.lock.unsafe;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoLockService {

  private final NoLockDataRepository repository;

  public NoLockService(NoLockDataRepository repository) {
    this.repository = repository;
  }

  public NoLock save() {
    return repository.save(new NoLock());
  }

  public NoLock find(Long id) {
    NoLock noLock = repository.findById(id).orElseThrow();
    noLock.increaseCount();
    return noLock;
  }
}