package crapeter.proj.PostgreSQL_SMS.Service;

import crapeter.proj.PostgreSQL_SMS.Model.Admin;
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
    List<Teacher> teachers = teacherRepo.findAll();
    List<TeacherInfoDTO> teacherDTO =  InfoDTO_Service.removeTeachersPrivateInfo(teacherRepo.findAll());
    for (Teacher teacher : teachers) {
      int size = studentRepo.findByTeacher_TeacherID(teacher.getTeacherID()).size();
      teacherDTO.get(teachers.indexOf(teacher)).setNumOfStudents(size);
    }
    return teacherDTO;
  }

  public Admin hireAdmin(Admin admin) {
    admin.setPassword(encrypt(admin.getPassword()));
    return adminRepo.save(admin);
  }

  public Teacher hireTeacher(Teacher teacher) {
    teacher.setPassword(encrypt(teacher.getPassword()));
    return teacherRepo.save(teacher);
  }

  public boolean authenticateAdmin(String email, String password) {
    return adminRepo.findByEmail(email).getPassword().equals(encrypt(password));
  }

  public void fireTeacher(String email, String password) {
    teacherRepo.deleteById(teacherRepo.findByEmail(email).getTeacherID());
  }

  public void fireAdmin(String email, String password) {
    if (authenticateAdmin(email, password)) {
      adminRepo.delete(adminRepo.findByEmail(email));
    }
  }

  public void moveClassToNewTeacher(String oldTeacherEmail, String newTeacherEmail) {
    Long oldTeacherID = teacherRepo.findByEmail(oldTeacherEmail).getTeacherID();
    Long newTeacherID = teacherRepo.findByEmail(newTeacherEmail).getTeacherID();
    List<Student> students = studentRepo.findByTeacher_TeacherID(oldTeacherID);
    for (Student student : students) {
      student.setTeacher(teacherRepo.findById(newTeacherID).orElse(null));
    }
    teacherRepo.delete(teacherRepo.findByEmail(oldTeacherEmail));
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
