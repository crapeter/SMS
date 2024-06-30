import React from "react"
import { useNavigate } from "react-router-dom"
import "./ToRegisterTeacher.css"

const Logout = () => {
  const nav = useNavigate()

  const handleLogout = (event) => {
    event.preventDefault()
    nav(`/register/teacher`)
  }

  return (
    <div className="logout_button">
      <button onClick={handleLogout} className="register_teacher_button">
        New Teacher Registration
      </button>
    </div>
  )
}

export default Logout
