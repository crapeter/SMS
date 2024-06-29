import React, { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import { useAuth } from "../Misc/AuthContext"
import Logout from "../Misc/Logout"
import axios from "axios"
import './ListStudentInfo.css'

const ListStudentInfo = () => {
  const { isLoggedIn } = useAuth()
  const { username } = useParams()
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

  return (
    <div>
      {isLoggedIn ? (
        <div className="im_the_highest_stud_div">
          <div className="student_info">
            <h1>{student.firstName} {student.lastName}</h1>
            <p className="info">Math: {student.mathGrade}</p>
            <p className="info">ELA: {student.elaGrade}</p>
            <p className="info">Science: {student.scienceGrade}</p>
            <p className="info">History: {student.historyGrade}</p>
            <p className="info">Reading: {student.readingGrade}</p>
            <Logout />
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

export default ListStudentInfo
