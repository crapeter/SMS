import React from "react"
import { useNavigate } from "react-router-dom"
import "./ToRegisterAdmin.css"

const Logout = () => {
  const nav = useNavigate()

  const handleLogout = (event) => {
    event.preventDefault()
    nav(`/register/admin`)
  }

  return (
    <div className="logout_button">
      <button onClick={handleLogout} className="register_admin_button">
        Register an Admin
      </button>
    </div>
  )
}

export default Logout
