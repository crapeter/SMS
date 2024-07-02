package crapeter.proj.PostgreSQL_SMS.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admins")
public class Admin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long adminID;

  @JsonProperty("FirstName")
  @Column(nullable = false)
  private String firstName;

  @JsonProperty("LastName")
  @Column(nullable = false)
  private String lastName;

  @JsonProperty("email")
  @Column(nullable = false, unique = true)
  private String email;

  @JsonProperty("password")
  @Column(nullable = false)
  private String password;
}
