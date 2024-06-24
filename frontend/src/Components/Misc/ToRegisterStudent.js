import React from "react"
import { useNavigate } from "react-router-dom"
import "./ToRegisterStudent.css"

const Logout = ({ email }) => {
  const nav = useNavigate()

  const handleLogout = (event) => {
    event.preventDefault()
    nav(`/register/student/${email}`)
  }

  return (
    <div className="logout_button">
      <button onClick={handleLogout} className="register_student_button">
        Register a Student
      </button>
    </div>
  )
}

export default Logout
