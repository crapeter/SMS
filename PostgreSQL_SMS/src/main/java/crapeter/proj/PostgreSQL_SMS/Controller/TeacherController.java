package crapeter.proj.PostgreSQL_SMS.Controller;

import crapeter.proj.PostgreSQL_SMS.GradeBook.GradeBook;
import crapeter.proj.PostgreSQL_SMS.Model.Student;
import crapeter.proj.PostgreSQL_SMS.Model.StudentInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Service.StudentService;
import crapeter.proj.PostgreSQL_SMS.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
  @Autowired
  private TeacherService teacherService;

  @Autowired
  private StudentService studentService;

  // Functions used to log in
  @GetMapping("/login/{email}/{password}")
  public ResponseEntity<Long> login(@PathVariable String email, @PathVariable String password) {
    boolean authenticated = teacherService.authenticateTeacher(email, password);
    if (authenticated) {
      Long teacherID = teacherService.getTeacherID(email);
      return ResponseEntity.ok(teacherID);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/get/id")
  public ResponseEntity<Long> getTeacherID(@RequestParam String email) {
    Long teacherID = teacherService.getTeacherID(email);
    return ResponseEntity.ok(teacherID);
  }

  @GetMapping("/get/email")
  public ResponseEntity<String> getTeacherEmail(@RequestParam Long teacherID) {
    String email = teacherService.getTeacherEmail(teacherID);
    return ResponseEntity.ok(email);
  }

  // Post-logging in
  @PostMapping("/add/student/{email}")
  public ResponseEntity<String> addStudentToTeacher(@PathVariable String email, @RequestBody Student student) {
    if (teacherService.addNewStudentToTeacher(email, student))
      return ResponseEntity.ok("Student added to teacher successfully");
    else
      return ResponseEntity.ok("error");
  }

  @PostMapping("/update/grades/{teacherEmail}/{username}")
  public ResponseEntity<String> updateStudentGrades(@PathVariable String teacherEmail, @PathVariable String username, @RequestBody GradeBook newGrades) {
    if (!teacherService.authenticateTeacher(teacherEmail, teacherService.getTeacherPassword(teacherEmail)))
      return ResponseEntity.badRequest().body("Invalid teacher credentials");
    String result = studentService.mapGradeToStudent(username, newGrades);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/change/name")
  public ResponseEntity<String> changeFirstName(@RequestParam String email, @RequestParam String newFirstName, @RequestParam String newLastName) {
    teacherService.changeName(email, newFirstName, newLastName);
    return ResponseEntity.ok("First name changed successfully");
  }

  @GetMapping("/get/students/{email}")
  public List<StudentInfoDTO> getTeachersStudents(@PathVariable String email) {
    Long teacherID = teacherService.getTeacherID(email);
    return teacherService.listTeachersStudents(teacherID);
  }

  @DeleteMapping("/remove/student")
  public ResponseEntity<String> removeStudentFromTeacher(@RequestParam String teacherEmail, @RequestParam String studentUsername) {
    studentService.removeStudent(teacherEmail, studentUsername);
    return ResponseEntity.ok("Student removed from teacher successfully");
  }
}
