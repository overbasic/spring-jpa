package basic.lock.safe;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private Long age = 0L;

  @Version
  private Long version;

  @OneToMany(mappedBy = "member")
  List<Car> cars = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public Long getAge() {
    return age;
  }

  public Long getVersion() {
    return version;
  }

  public void increaseCount() {
    age++;
  }
}