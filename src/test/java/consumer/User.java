package consumer;

public class User {
  private Long id;
  private String username;
  private Integer age;

  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public Integer getAge() {
    return this.age;
  }

  public void setAge(final Integer age) {
    this.age = age;
  }
}
