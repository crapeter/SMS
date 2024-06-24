package crapeter.proj.PostgreSQL_SMS.Service;

import crapeter.proj.PostgreSQL_SMS.GradeBook.GradeBook;
import crapeter.proj.PostgreSQL_SMS.Model.Student;
import crapeter.proj.PostgreSQL_SMS.Model.StudentInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Model.Teacher;
import crapeter.proj.PostgreSQL_SMS.Repo.StudentRepo;
import crapeter.proj.PostgreSQL_SMS.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StudentService {
  @Autowired
  private StudentRepo studentRepo;

  @Autowired
  private TeacherRepo teacherRepo;

  public boolean authenticateStudent(String username, String password) {
    Student student = studentRepo.findByUsername(username);
    return student.getPassword().equals(password);
  }

  public StudentInfoDTO getStudentInfo(String username) {
    Student student = studentRepo.findByUsername(username);
    return InfoDTO_Service.removePrivateInfo(student);
  }

  public String mapGradeToStudent(String username, GradeBook newGrades) {
    Student student = studentRepo.findByUsername(username);
    student.setMathGrade(newGrades.getMathGrade());
    student.setElaGrade(newGrades.getElaGrade());
    student.setScienceGrade(newGrades.getScienceGrade());
    student.setHistoryGrade(newGrades.getHistoryGrade());
    student.setReadingGrade(newGrades.getReadingGrade());
    studentRepo.save(student);
    return "Grades updated successfully";
  }

  public void removeStudent(String email, String username) {
    Teacher teacher = teacherRepo.findByEmail(email);
    Student student = studentRepo.findByUsername(username);
    if (!Objects.equals(student.getTeacher().getTeacherID(), teacher.getTeacherID()))
      return;
    studentRepo.delete(studentRepo.findByUsername(username));
  }

  public void updateFirstName(String email, String username, String newFirstName) {
    Teacher teacher = teacherRepo.findByEmail(email);
    Student student = studentRepo.findByUsername(username);
    if (!Objects.equals(student.getTeacher().getTeacherID(), teacher.getTeacherID()))
      return;
    student.setFirstName(newFirstName);
    studentRepo.save(student);
  }

  public void updateLastName(String email, String username, String newLastName) {
    Teacher teacher = teacherRepo.findByEmail(email);
    Student student = studentRepo.findByUsername(username);
    if (!Objects.equals(student.getTeacher().getTeacherID(), teacher.getTeacherID()))
      return;
    student.setLastName(newLastName);
    studentRepo.save(student);
  }
}
