package basic.lock.safe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

}