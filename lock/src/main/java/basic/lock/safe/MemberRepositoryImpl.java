package basic.lock.safe;


import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class MemberRepositoryImpl implements MemberRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final EntityManager entityManager;

  public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.entityManager = entityManager;
  }

  @Override
  public Member save(Member member) {
    entityManager.persist(member);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(QMember.member)
        .where(QMember.member.id.eq(id))
        .fetchOne());
  }

  @Override
  public Optional<Member> findByIdWithPessimisticWrite(Long id) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(QMember.member)
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .where(QMember.member.id.eq(id))
        .fetchOne());
  }

  @Override
  public Optional<Member> findByIdWithPessimisticForceIncrement(Long id) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(QMember.member)
        .setLockMode(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
        .where(QMember.member.id.eq(id))
        .fetchOne());
  }

  @Override
  public Optional<Member> findByIdWithOptimistic(Long id) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(QMember.member)
        .setLockMode(LockModeType.OPTIMISTIC)
        .where(QMember.member.id.eq(id))
        .fetchOne());
  }

  @Override
  public Optional<Member> findByIdWithOptimisticForceIncrement(Long id) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(QMember.member)
        .setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
        .where(QMember.member.id.eq(id))
        .fetchOne());
  }
}