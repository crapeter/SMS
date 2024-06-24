import React from "react"
import { useNavigate } from "react-router-dom"
import "./ToRegisterStudent.css"

const Logout = () => {
  const nav = useNavigate()

  const handleLogout = (event) => {
    event.preventDefault()
    nav(`/register/teacher`)
  }

  return (
    <div className="logout_button">
      <button onClick={handleLogout} className="actual_login_button_for_real">
        New Teacher Registration
      </button>
    </div>
  )
}

export default Logout
