import React from "react"
import { useNavigate } from "react-router-dom"
import { useAuth } from "./AuthContext"
import "./Logout.css"

const Logout = () => {
  const { setIsLoggedIn, setIsAdmin } = useAuth()
  const nav = useNavigate()

  const handleLogout = (event) => {
    event.preventDefault()
    setIsLoggedIn(false)
    setIsAdmin(false)
    nav("/")
  }

  return (
    <div className="logout_button">
      <button onClick={handleLogout} className="actual_logout_button">
        Log out
      </button>
    </div>
  )
}

export default Logout
