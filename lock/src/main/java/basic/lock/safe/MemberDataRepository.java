package basic.lock.safe;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberDataRepository extends JpaRepository<Member, Long>, MemberRepository {

  Optional<Member> findById(Long id);

  @Lock(LockModeType.OPTIMISTIC)
  @Query(value = "select m from Member m where m.id = :id")
  Optional<Member> findByIdWithOptimistic(@Param("id") Long id);

  @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
  @Query(value = "select m from Member m where m.id = :id")
  Optional<Member> findByIdWithOptimisticForceIncrement(@Param("id") Long id);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query(value = "select m from Member m where m.id = :id")
  Optional<Member> findByIdWithPessimisticWrite(@Param("id") Long id);

  @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
  @Query(value = "select m from Member m where m.id = :id")
  Optional<Member> findByIdWithPessimisticForceIncrement(@Param("id") Long id);
}