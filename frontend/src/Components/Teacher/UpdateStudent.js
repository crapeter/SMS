import React, { useEffect, useState } from "react"
import { useParams, useNavigate } from "react-router-dom"
import { useAuth } from "../Misc/AuthContext"
import UpdateGrades from '../Misc/ToUpdateGrades'
import UpdateStudentName from './UpdateStudentName'
import axios from "axios"
import './UpdateStudent.css'

const UpdateStudent = () => {
  const { isLoggedIn, isAdmin } = useAuth()
  const { email, teacherID, username } = useParams()
  const [student, setStudent] = useState({
    username: '',
    firstName: '',
    lastName: '',
    mathGrade: '',
    elaGrade: '',
    scienceGrade: '',
    historyGrade: '',
    readingGrade: '',
  })
  const nav = useNavigate()

  useEffect(() => {
    fecthStudentInfo()
    // eslint-disable-next-line
  }, [username])

  const fecthStudentInfo = () => {
    axios.get(`/api/student/info/${username}`)
      .then(response => {
        setStudent(response.data)
      })
      .catch(e => alert(e))
  }

  const goBack = () => {
    axios.get(`/api/teacher/get/email?teacherID=${teacherID}`)
      .then(response => {
        nav(`/teacher/list/${response.data}/${teacherID}`)
      })
      .catch(e => alert(e))
  }

  return (
    <div>
      {isLoggedIn && isAdmin ? (
        <div className="im_the_highest_div">
          <div className="student_info">
            <h1>{student.firstName} {student.lastName}</h1>
            <p className="info">Math: {student.mathGrade}</p>
            <p className="info">ELA: {student.elaGrade}</p>
            <p className="info">Science: {student.scienceGrade}</p>
            <p className="info">History: {student.historyGrade}</p>
            <p className="info">Reading: {student.readingGrade}</p>
            <div className="flex_time">
              <UpdateStudentName fName={student.firstName} lName={student.lastName} email={email} username={username} />
              <UpdateGrades email={email} teacherID={teacherID} username={username} />
              <button className="return_button" onClick={goBack}>
                Return
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div>
          <h1>You aren't logged in</h1>
        </div>
      )}
    </div>
  )
}

export default UpdateStudent
