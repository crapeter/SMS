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

  public Teacher addTeacher(Teacher teacher) {
    teacher.setPassword(encrypt(teacher.getPassword()));
    return teacherRepo.save(teacher);
  }

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

  public List<Teacher> getTeachers() {
    return teacherRepo.findAll();
  }

  public List<StudentInfoDTO> listTeachersStudents(Long teacherID) {
    List<Student> students = studentRepo.findByTeacher_TeacherID(teacherID);
    return InfoDTO_Service.removePrivateInfoList(students);
  }

  public boolean authenticateTeacher(String email, String password) {
    Teacher teacher = teacherRepo.findByEmail(email);
    return teacher.getPassword().equals(encrypt(password));
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

  public boolean moveStudentToTeacher(String email, String username) {
    Long teacherID = teacherRepo.findByEmail(email).getTeacherID();
    Long studentID = studentRepo.findByUsername(username).getStudentID();
    Optional<Teacher> optionalTeacher = teacherRepo.findById(teacherID);
    Optional<Student> optionalStudent = studentRepo.findById(studentID);
    if (optionalTeacher.isPresent() && optionalStudent.isPresent()) {
      Student student = optionalStudent.get();
      student.setTeacher(optionalTeacher.get());
      studentRepo.save(student);
      return true;
    }
    return false;
  }

  public boolean resign(String email) {
    Teacher teacher = teacherRepo.findByEmail(email);
    List<Student> teachersStudents = studentRepo.findByTeacher_TeacherID(teacher.getTeacherID());
    if (teachersStudents.isEmpty()) {
      teacherRepo.delete(teacher);
      return true;
    }
    return false;
  }

  public void changeFirstName(String email, String newFirstName) {
    Teacher teacher = teacherRepo.findByEmail(email);
    teacher.setFirstName(newFirstName);
    teacherRepo.save(teacher);
  }

  public void changeLastName(String email, String newLastName) {
    Teacher teacher = teacherRepo.findByEmail(email);
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
