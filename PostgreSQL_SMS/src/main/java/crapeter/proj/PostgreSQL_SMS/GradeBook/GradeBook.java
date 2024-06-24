package crapeter.proj.PostgreSQL_SMS.GradeBook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GradeBook {
  @JsonProperty("math")
  private Integer mathGrade;
  @JsonProperty("ela")
  private Integer elaGrade;
  @JsonProperty("science")
  private Integer scienceGrade;
  @JsonProperty("history")
  private Integer historyGrade;
  @JsonProperty("reading")
  private Integer readingGrade;
}
