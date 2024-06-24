package crapeter.proj.PostgreSQL_SMS.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long studentID;

  @ManyToOne
  @JoinColumn(name = "teacherID")
  @JsonProperty("teacher")
  private Teacher teacher;

  // Username and password for login
  @JsonProperty("username")
  @Column(nullable = false, unique = true)
  private String username;

  @JsonProperty("password")
  @Column(nullable = false)
  private String password;

  // Info that will be accessed and Displayed
  // Student's first and last name
  @JsonProperty("FirstName")
  @Column(nullable = false)
  private String firstName;

  @JsonProperty("LastName")
  @Column(nullable = false)
  private String lastName;

  // Grade Book (based on US elementary schools)
  // can be null to start as they won't have grades yet
  @Column()
  private Integer mathGrade;
  @Column()
  private Integer elaGrade;
  @Column()
  private Integer scienceGrade;
  @Column()
  private Integer historyGrade;
  @Column()
  private Integer readingGrade;
}
