package basic.lock.safe;

import java.util.Optional;

public interface MemberRepository {

  Member save(Member member);

  Optional<Member> findById(Long id);

  Optional<Member> findByIdWithPessimisticWrite(Long id);

  Optional<Member> findByIdWithPessimisticForceIncrement(Long id);

  Optional<Member> findByIdWithOptimistic(Long id);

  Optional<Member> findByIdWithOptimisticForceIncrement(Long id);
}