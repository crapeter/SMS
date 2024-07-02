package crapeter.proj.PostgreSQL_SMS.Service;

import crapeter.proj.PostgreSQL_SMS.Model.Student;
import crapeter.proj.PostgreSQL_SMS.Model.StudentInfoDTO;
import crapeter.proj.PostgreSQL_SMS.Model.Teacher;
import crapeter.proj.PostgreSQL_SMS.Model.TeacherInfoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class InfoDTO_Service {
  public static List<StudentInfoDTO> removePrivateInfoList(List<Student> students) {
    return students.stream().map(student -> {
      StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
      studentInfoDTO.setUsername(student.getUsername());
      studentInfoDTO.setFirstName(student.getFirstName());
      studentInfoDTO.setLastName(student.getLastName());
      studentInfoDTO.setMathGrade(student.getMathGrade());
      studentInfoDTO.setElaGrade(student.getElaGrade());
      studentInfoDTO.setScienceGrade(student.getScienceGrade());
      studentInfoDTO.setHistoryGrade(student.getHistoryGrade());
      studentInfoDTO.setReadingGrade(student.getReadingGrade());
      return studentInfoDTO;
    })
    .collect(Collectors.toList());
  }

  public static StudentInfoDTO removePrivateInfo(Student student) {
    StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
    studentInfoDTO.setUsername(student.getUsername());
    studentInfoDTO.setFirstName(student.getFirstName());
    studentInfoDTO.setLastName(student.getLastName());
    studentInfoDTO.setMathGrade(student.getMathGrade());
    studentInfoDTO.setElaGrade(student.getElaGrade());
    studentInfoDTO.setScienceGrade(student.getScienceGrade());
    studentInfoDTO.setHistoryGrade(student.getHistoryGrade());
    studentInfoDTO.setReadingGrade(student.getReadingGrade());
    return studentInfoDTO;
  }

  public static List<TeacherInfoDTO> removeTeachersPrivateInfo(List<Teacher> teachers) {
    return teachers.stream().map(teacher -> {
      TeacherInfoDTO teacherInfoDTO = new TeacherInfoDTO();
      teacherInfoDTO.setFirstName(teacher.getFirstName());
      teacherInfoDTO.setLastName(teacher.getLastName());
      teacherInfoDTO.setEmail(teacher.getEmail());
      return teacherInfoDTO;
    })
    .collect(Collectors.toList());
  }
}
