// import logo from './logo.svg'
import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './Components/Misc/AuthContext';
import Home_Page from './Components/Misc/HomePage'
import Teacher_Login from './Components/Login/TeacherLogin.js'
import Student_Login from './Components/Login/StudentLogin.js'
import Student_Info from './Components/Student/ListStudentInfo'
import Register_Student from './Components/Register/RegisterStudent'
import Register_Teacher from './Components/Register/RegisterTeacher'
import Student_List from './Components/Teacher/ListStudents'
import Update_Student from './Components/Teacher/UpdateStudent'
import Update_Grades from './Components/Teacher/UpdateGrades'
import './App.css';

function App() {
  return (
    <AuthProvider>
      <div className="App">
        <BrowserRouter>
          <Routes>
            <Route exact path="/" Component={Home_Page} />
            <Route exact path="/teacher/login" Component={Teacher_Login} />
            <Route exact path="/teacher/list/:email/:teacherID" Component={Student_List} />
            <Route exact path="/teacher/update/student/:email/:teacherID/:username" Component={Update_Student} />
            <Route exact path="/teacher/update/student/grades/:email/:teacherID/:username" Component={Update_Grades} />
            <Route exact path="/register/teacher" Component={Register_Teacher} />
            <Route exact path="/register/student/:email" Component={Register_Student} />
            <Route exact path="/student/login" Component={Student_Login} />
            <Route exact path="/student/info/:username" Component={Student_Info} />
          </Routes>
        </BrowserRouter>
      </div>
    </AuthProvider>
  );
}

export default App;
