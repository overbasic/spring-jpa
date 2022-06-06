package basic.lock.safe;

import static javax.persistence.LockModeType.OPTIMISTIC;
import static javax.persistence.LockModeType.OPTIMISTIC_FORCE_INCREMENT;
import static javax.persistence.LockModeType.PESSIMISTIC_FORCE_INCREMENT;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MemberJpaRepository implements MemberRepository {

  private final EntityManager entityManager;

  public MemberJpaRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Member save(Member member) {
    entityManager.persist(member);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    Member member = entityManager.find(Member.class, id);
    return Optional.of(member);
  }

  @Override
  public Optional<Member> findByIdWithPessimisticWrite(Long id) {
    Member member = entityManager.find(Member.class, id, PESSIMISTIC_WRITE);
    return Optional.ofNullable(member);
  }

  @Override
  public Optional<Member> findByIdWithPessimisticForceIncrement(Long id) {
    Member member = entityManager.find(Member.class, id, PESSIMISTIC_FORCE_INCREMENT);
    return Optional.ofNullable(member);
  }

  @Override
  public Optional<Member> findByIdWithOptimistic(Long id) {
    Member member = entityManager.find(Member.class, id, OPTIMISTIC);
    return Optional.ofNullable(member);
  }

  @Override
  public Optional<Member> findByIdWithOptimisticForceIncrement(Long id) {
    Member member = entityManager.find(Member.class, id, OPTIMISTIC_FORCE_INCREMENT);
    return Optional.ofNullable(member);
  }
}