package basic.lock.unsafe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NoLock {

  @Id
  @GeneratedValue
  private Long id;

  private Long count = 0L;

  public Long getId() {
    return id;
  }

  public Long getCount() {
    return count;
  }

  public void increaseCount(){
    count++;
  }
}