package crapeter.proj.PostgreSQL_SMS.Model;

import lombok.Data;

@Data
public class StudentInfoDTO {
  private String username;
  private String firstName;
  private String lastName;
  private Integer mathGrade;
  private Integer elaGrade;
  private Integer scienceGrade;
  private Integer historyGrade;
  private Integer readingGrade;
}
