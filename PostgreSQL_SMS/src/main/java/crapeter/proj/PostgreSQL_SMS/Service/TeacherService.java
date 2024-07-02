package crapeter.proj.PostgreSQL_SMS.Service;

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
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
  @Autowired
  private TeacherRepo teacherRepo;

  @Autowired
  private StudentRepo studentRepo;

  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
  private static final String SECRET_KEY = "0123456789abcdef";
  private static final String INIT_VECTOR = "abcdef9876543210";

  public Long getTeacherID(String email) {
    return teacherRepo.findByEmail(email).getTeacherID();
  }

  public String getTeacherEmail(Long teacherID) {
    Optional<Teacher> optionalTeacher = teacherRepo.findById(teacherID);
    return optionalTeacher.map(Teacher::getEmail).orElse(null);
  }

  public String getTeacherPassword(String email) {
    return teacherRepo.findByEmail(email).getPassword();
  }

  public List<StudentInfoDTO> listTeachersStudents(Long teacherID) {
    List<Student> students = studentRepo.findByTeacher_TeacherID(teacherID);
    return InfoDTO_Service.removePrivateInfoList(students);
  }

  public boolean authenticateTeacher(String email, String password) {
    Teacher teacher = teacherRepo.findByEmail(email);
    return teacher.getPassword().equals(encrypt(password)) || teacher.getPassword().equals(password);
  }

  public boolean addNewStudentToTeacher(String email, Student student) {
    Long teacherID = teacherRepo.findByEmail(email).getTeacherID();
    Optional<Teacher> optionalTeacher = teacherRepo.findById(teacherID);
    if (optionalTeacher.isPresent()) {
      student.setPassword(encrypt(student.getPassword()));
      student.setTeacher(optionalTeacher.get());
      studentRepo.save(student);
      return true;
    }
    return false;
  }

  public void changeName(String email, String newFirstName, String newLastName) {
    Teacher teacher = teacherRepo.findByEmail(email);
    teacher.setFirstName(newFirstName);
    teacher.setLastName(newLastName);
    teacherRepo.save(teacher);
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
