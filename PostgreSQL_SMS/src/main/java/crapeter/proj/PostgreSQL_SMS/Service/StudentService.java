package crapeter.proj.PostgreSQL_SMS.Service;

import crapeter.proj.PostgreSQL_SMS.GradeBook.GradeBook;
import crapeter.proj.PostgreSQL_SMS.Model.Student;
import crapeter.proj.PostgreSQL_SMS.Model.StudentInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Model.Teacher;
import crapeter.proj.PostgreSQL_SMS.Repo.StudentRepo;
import crapeter.proj.PostgreSQL_SMS.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Objects;

@Service
public class StudentService {
  @Autowired
  private StudentRepo studentRepo;

  @Autowired
  private TeacherRepo teacherRepo;

  private static final String ALGORITHM = System.getenv("ALGORITHM");
  private static final String SECRET_KEY = System.getenv("SECRET_KEY");
  private static final String INIT_VECTOR = System.getenv("INIT_VECTOR");

  public boolean authenticateStudent(String username, String password) {
    Student student = studentRepo.findByUsername(username);
    return student.getPassword().equals(encrypt(password));
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

  public void updateName(String email, String username, String newFirstName, String newLastName) {
    Teacher teacher = teacherRepo.findByEmail(email);
    Student student = studentRepo.findByUsername(username);
    if (!Objects.equals(student.getTeacher().getTeacherID(), teacher.getTeacherID()))
      return;
    student.setFirstName(newFirstName);
    student.setLastName(newLastName);
    studentRepo.save(student);
  }

  private String encrypt(String password) {
    try {
      IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes());
      SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

      byte[] encrypted = cipher.doFinal(password.getBytes());
      return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception ignored) {
    }
    return null;
  }
}
