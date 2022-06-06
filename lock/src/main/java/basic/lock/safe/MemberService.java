package basic.lock.safe;

import javax.persistence.LockModeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class MemberService {

  private final MemberRepository repository;

  public MemberService(MemberRepository repository) {
    this.repository = repository;
  }

  public Member save() {
    return repository.save(new Member());
  }

  public Member findMember(Long id) {
    return repository.findById(id).orElseThrow();
  }

  public Member findMemberWithLock(Long id, LockModeType lockModeType) {
    Member member = switch (lockModeType) {
      case PESSIMISTIC_WRITE -> repository.findByIdWithPessimisticWrite(id).orElseThrow();
      case OPTIMISTIC_FORCE_INCREMENT ->
          repository.findByIdWithOptimisticForceIncrement(id).orElseThrow();
      case OPTIMISTIC -> repository.findByIdWithOptimistic(id).orElseThrow();
      case PESSIMISTIC_FORCE_INCREMENT ->
          repository.findByIdWithPessimisticForceIncrement(id).orElseThrow();
      default -> repository.findById(id).orElseThrow();
    };

    member.increaseCount();
    return member;
  }
}