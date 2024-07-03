import React from "react"
import { Button } from 'react-bootstrap'
import { useNavigate } from "react-router-dom"
import "./Login.css"

const Resign = () => {
  const nav = useNavigate()

  const boardLogin = (event) => {
    event.preventDefault()
    nav("/admin/login")
  }

  const teacherLogin = (event) => {
    event.preventDefault()
    nav("/teacher/login")
  }

  const studentLogin = (event) => {
    event.preventDefault()
    nav("/student/login")
  }

  return (
    <div className="login_button">
      <Button onClick={boardLogin} className="actual_login_button_for_real">
        Admin Login
      </Button>
      <Button onClick={teacherLogin} className="actual_login_button_for_real">
        Teacher Login
      </Button>
      <Button onClick={studentLogin} className="actual_login_button_for_real">
        Student Login
      </Button>
    </div>
  )
}

export default Resign
