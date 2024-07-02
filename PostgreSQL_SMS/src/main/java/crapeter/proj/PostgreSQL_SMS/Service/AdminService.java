package crapeter.proj.PostgreSQL_SMS.Service;

import crapeter.proj.PostgreSQL_SMS.Model.Student;
import crapeter.proj.PostgreSQL_SMS.Model.Teacher;
import crapeter.proj.PostgreSQL_SMS.Model.TeacherInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Repo.AdminRepo;
import crapeter.proj.PostgreSQL_SMS.Repo.StudentRepo;
import crapeter.proj.PostgreSQL_SMS.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Service
public class AdminService {
  @Autowired
  private AdminRepo adminRepo;

  @Autowired
  private TeacherRepo teacherRepo;

  @Autowired
  private StudentRepo studentRepo;

  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
  private static final String SECRET_KEY = "0123456789abcdef";
  private static final String INIT_VECTOR = "abcdef9876543210";

  public List<TeacherInfoDTO> getTeachers() {
    return InfoDTO_Service.removeTeachersPrivateInfo(teacherRepo.findAll());
  }

  public boolean authenticateAdmin(String email, String password) {
    return adminRepo.findByEmail(email).getPassword().equals(encrypt(password));
  }

  public Teacher hireTeacher(Teacher teacher) {
    teacher.setPassword(encrypt(teacher.getPassword()));
    return teacherRepo.save(teacher);
  }

  public void moveClassToNewTeacher(String oldTeacherEmail, String newTeacherEmail) {
    Long oldTeacherID = teacherRepo.findByEmail(oldTeacherEmail).getTeacherID();
    Long newTeacherID = teacherRepo.findByEmail(newTeacherEmail).getTeacherID();
    List<Student> students = studentRepo.findByTeacher_TeacherID(oldTeacherID);
    for (Student student : students) {
      student.setTeacher(teacherRepo.findById(newTeacherID).orElse(null));
    }
    teacherRepo.deleteByEmail(oldTeacherEmail);
  }

  public void moveStudentToNewTeacher(String newTeacherEmail, String username) {
    Long newTeacherID = teacherRepo.findByEmail(newTeacherEmail).getTeacherID();
    Student student = studentRepo.findByUsername(username);
    if (student != null) {
      student.setTeacher(teacherRepo.findById(newTeacherID).orElse(null));
      studentRepo.save(student);
    }
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
