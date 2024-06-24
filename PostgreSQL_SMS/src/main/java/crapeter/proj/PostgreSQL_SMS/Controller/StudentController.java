package crapeter.proj.PostgreSQL_SMS.Controller;

import crapeter.proj.PostgreSQL_SMS.Model.StudentInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Service.StudentService;
import crapeter.proj.PostgreSQL_SMS.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @Autowired
  private TeacherService teacherService;

  @GetMapping("/login/{username}/{password}")
  public ResponseEntity<Boolean> login(@PathVariable String username, @PathVariable String password) {
    boolean authenticated = studentService.authenticateStudent(username, password);
    return ResponseEntity.ok(authenticated);
  }

  @PutMapping("/update/first/name")
  public ResponseEntity<String> updateFirstName(@RequestParam String teacherEmail, @RequestParam String username, @RequestParam String newFirstName) {
    studentService.updateFirstName(teacherEmail, username, newFirstName);
    return ResponseEntity.ok("First name updated successfully");
  }

  @PutMapping("/update/last/name")
  public ResponseEntity<String> updateLastName(@RequestParam String teacherEmail, @RequestParam String username, @RequestParam String newLastName) {
    studentService.updateLastName(teacherEmail, username, newLastName);
    return ResponseEntity.ok("Last name updated successfully");
  }

  // This is the only function that students will have access to
  @GetMapping("/info/{username}")
  public ResponseEntity<StudentInfoDTO> getStudentInfo(@PathVariable String username) {
    return ResponseEntity.ok(studentService.getStudentInfo(username));
  }
}
