import React, { useState } from 'react';
import { Form } from 'react-bootstrap';
import { useNavigate } from "react-router-dom"
import { useAuth } from "../Misc/AuthContext"
import './StudentLogin.css';
import axios from 'axios'

const StudentLogin = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const { setIsLoggedIn, setIsAdmin } = useAuth()
  const nav = useNavigate()

  const handleLogin = (e) => {
    e.preventDefault()
    axios.get(`/student/login/${username}/${password}`)
      .then(response => {
        if (response.data) {
          setIsLoggedIn(true)
          setIsAdmin(false)
          nav(`/student/info/${username}`)
        } else {
          alert("Incorrect username or password.")
        }
      })
      .catch(e => alert(e))
  }

  const goBack = () => {
    nav(`/`)
  }

  return (
    <div className='top_stud_div'>
      <div className='StudentLogin'>
        <h2 className='login_text'>Login</h2>
        <Form>
          <Form.Group controlId="formBasicEmail">
            <Form.Control
              className='student_input'
              type="email"
              placeholder="Enter username"
              onChange={e => setUsername(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Control
              className='student_input'
              type="password"
              placeholder="Enter password"
              onChange={e => setPassword(e.target.value)}
            />
          </Form.Group>
          <button className="student_button" onClick={handleLogin}>
            Log in
          </button>
          <button className="student_button" onClick={goBack}>
            Return
          </button>
        </Form>
      </div>
    </div>
  )
}

export default StudentLogin

