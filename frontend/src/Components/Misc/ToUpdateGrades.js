import React from "react"
import { useNavigate } from "react-router-dom"
import "./ToUpdateGrades.css"

const Logout = ({ email, teacherID, username }) => {
  const nav = useNavigate()

  const handleLogout = (event) => {
    event.preventDefault()
    nav(`/teacher/update/student/grades/${email}/${teacherID}/${username}`)
  }

  return (
    <div className="logout_button">
      <button onClick={handleLogout} className="go_to_update_grades_page_button">
        Update Grades
      </button>
    </div>
  )
}

export default Logout
