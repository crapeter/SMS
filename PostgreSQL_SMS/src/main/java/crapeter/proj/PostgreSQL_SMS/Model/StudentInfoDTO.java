package crapeter.proj.PostgreSQL_SMS.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudentInfoDTO {
  @JsonProperty("username")
  private String username;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("mathGrade")
  private Integer mathGrade;

  @JsonProperty("elaGrade")
  private Integer elaGrade;

  @JsonProperty("scienceGrade")
  private Integer scienceGrade;

  @JsonProperty("historyGrade")
  private Integer historyGrade;

  @JsonProperty("readingGrade")
  private Integer readingGrade;
}
