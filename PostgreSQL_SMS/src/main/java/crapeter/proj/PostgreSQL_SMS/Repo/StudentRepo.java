package crapeter.proj.PostgreSQL_SMS.Repo;

import crapeter.proj.PostgreSQL_SMS.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StudentRepo extends JpaRepository<Student, Long>{
  List<Student> findByTeacher_TeacherID(Long teacherID);
  Student findByUsername(String username);
}
