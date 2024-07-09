package crapeter.proj.PostgreSQL_SMS.Controller;

import crapeter.proj.PostgreSQL_SMS.Model.StudentInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @GetMapping("/login/{username}/{password}")
  public ResponseEntity<Boolean> login(@PathVariable String username, @PathVariable String password) {
    boolean authenticated = studentService.authenticateStudent(username, password);
    return ResponseEntity.ok(authenticated);
  }

  @PostMapping("/update/name/{email}/{username}/{newFirstName}/{newLastName}")
  public ResponseEntity<String> updateName(@PathVariable String email, @PathVariable String username, @PathVariable String newFirstName, @PathVariable String newLastName) {
    studentService.updateName(email, username, newFirstName, newLastName);
    return ResponseEntity.ok("First name updated successfully");
  }

  // This is the only function that students will have access to
  @GetMapping("/info/{username}")
  public ResponseEntity<StudentInfoDTO> getStudentInfo(@PathVariable String username) {
    return ResponseEntity.ok(studentService.getStudentInfo(username));
  }
}
