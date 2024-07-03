package crapeter.proj.PostgreSQL_SMS.Controller;

import crapeter.proj.PostgreSQL_SMS.Model.Admin;
import crapeter.proj.PostgreSQL_SMS.Model.Teacher;
import crapeter.proj.PostgreSQL_SMS.Model.TeacherInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
  @Autowired
  private AdminService adminService;

  @PostMapping("/login/{email}/{password}")
  public ResponseEntity<Boolean> login(@PathVariable String email, @PathVariable String password) {
    boolean authenticated = adminService.authenticateAdmin(email, password);
    return ResponseEntity.ok(authenticated);
  }

  @PostMapping("/add/admin")
  public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
    Admin newAdmin = adminService.hireAdmin(admin);
    if (newAdmin != null) {
      return ResponseEntity.ok("Admin added successfully");
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/add/teacher")
  public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher) {
    Teacher newTeacher = adminService.hireTeacher(teacher);
    if (newTeacher != null) {
      return ResponseEntity.ok("Teacher added successfully");
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/move/student")
  public ResponseEntity<String> moveStudent(@RequestParam String newTeacherEmail, @RequestParam String username) {
    adminService.moveStudentToNewTeacher(newTeacherEmail, username);
    return ResponseEntity.ok("Student moved successfully");
  }

  @GetMapping("/get/teachers")
  public List<TeacherInfoDTO> getTeachers() {
    return adminService.getTeachers();
  }

  @DeleteMapping("/move/class")
  public ResponseEntity<String> removeTeacher(@RequestParam String oldTeacherEmail, @RequestParam String newTeacherEmail) {
    adminService.moveClassToNewTeacher(oldTeacherEmail, newTeacherEmail);
    return ResponseEntity.ok("Class moved successfully");
  }

  @DeleteMapping("/remove")
  public ResponseEntity<String> removeAdmin(@RequestParam String email, @RequestParam String password) {
    adminService.fireTeacher(email, password);
    return ResponseEntity.ok("Admin removed successfully");
  }
}
