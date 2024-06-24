package crapeter.proj.PostgreSQL_SMS.Controller;

import crapeter.proj.PostgreSQL_SMS.GradeBook.GradeBook;
import crapeter.proj.PostgreSQL_SMS.Model.Student;
import crapeter.proj.PostgreSQL_SMS.Model.StudentInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Model.Teacher;
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

  // Pre-logging in
  @PostMapping("/add")
  public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher) {
    Teacher newTeacher = teacherService.addTeacher(teacher);
    if (newTeacher != null) {
      return ResponseEntity.ok("Teacher added successfully");
    }
    return ResponseEntity.notFound().build();
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

  @GetMapping("/get/all")
  public List<Teacher> getTeacher() {
    return teacherService.getTeachers();
  }

  @GetMapping("/get/students/{email}")
  public List<StudentInfoDTO> getTeachersStudents(@PathVariable String email) {
    Long teacherID = teacherService.getTeacherID(email);
    return teacherService.listTeachersStudents(teacherID);
  }

  @PutMapping("/move/student")
  public ResponseEntity<String> moveStudentToTeacher(@RequestParam String username, @RequestParam String email) {
    if (teacherService.moveStudentToTeacher(email, username))
      return ResponseEntity.ok("Student moved to teacher successfully");
    else
      return ResponseEntity.notFound().build();
  }

  @PutMapping("/change/firstName")
  public ResponseEntity<String> changeFirstName(@RequestParam String email, @RequestParam String newFirstName) {
    teacherService.changeFirstName(email, newFirstName);
    return ResponseEntity.ok("First name changed successfully");
  }

  @PutMapping("/change/lastName")
  public ResponseEntity<String> changeLastName(@RequestParam String email, @RequestParam String newLastName) {
    teacherService.changeLastName(email, newLastName);
    return ResponseEntity.ok("Last name changed successfully");
  }

  @DeleteMapping("/remove/student")
  public ResponseEntity<String> removeStudentFromTeacher(@RequestParam String teacherEmail, @RequestParam String studentUsername) {
    studentService.removeStudent(teacherEmail, studentUsername);
    return ResponseEntity.ok("Student removed from teacher successfully");
  }

  @DeleteMapping("/resign")
  public ResponseEntity<String> resign(@RequestParam String email) {
    boolean canResign = teacherService.resign(email);
    if (!canResign)
      return ResponseEntity.badRequest().body("Teacher has students");
    return ResponseEntity.ok("Teacher resigned successfully");
  }
}
