package basic.lock.unsafe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoLockDataRepository extends JpaRepository<NoLock, Long> {

}