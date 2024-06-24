package crapeter.proj.PostgreSQL_SMS.Repo;

import crapeter.proj.PostgreSQL_SMS.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
  Teacher findByEmail(String email);
}
