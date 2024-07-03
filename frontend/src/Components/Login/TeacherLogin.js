import React, { useState } from 'react'
import { Form } from 'react-bootstrap'
import { useNavigate } from "react-router-dom"
import { useAuth } from "../Misc/AuthContext"
import axios from 'axios'
import './TeacherLogin.css'
import '../Misc/Logout.css'


const TeacherLogin = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const { setIsLoggedIn, setIsAdmin } = useAuth()
  const nav = useNavigate()

  const handleLogin = (e) => {
    e.preventDefault()
    axios.get(`/api/teacher/login/${email}/${password}`)
      .then(response => {
        if (Number.isInteger(response.data)) {
          setIsLoggedIn(true)
          setIsAdmin(true)
          nav(`/teacher/list/${email}/${response.data}`)
        } else {
          alert("Incorrect email or password.")
        }
      })
      .catch(e => alert(e))
  }

  const goBack = () => {
    nav(`/`)
  }

  return (
    <div className='top_tea_div'>
      <div className='TeacherLogin'>
        <h2 className='login_text'>Login</h2>
        <Form>
          <Form.Group controlId="formBasicEmail">
            <Form.Control
              className='teacher_input'
              type="text"
              placeholder="Enter email"
              onChange={e => setEmail(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Control
              className='teacher_input'
              type="password"
              placeholder="Enter password"
              onChange={e => setPassword(e.target.value)}
            />
          </Form.Group>
          <button className="teacher_button" type="submit" onClick={handleLogin}>
            Log in
          </button>
          <button className="return_button" type="submit" onClick={goBack}>
            Return
          </button>
        </Form>
      </div>
    </div>
  )
}

export default TeacherLogin

