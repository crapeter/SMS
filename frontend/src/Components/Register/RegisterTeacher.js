import React, { useState } from 'react';
import { useNavigate } from "react-router-dom"
import { Form } from 'react-bootstrap';
import axios from 'axios'
import './RegisterTeacher.css'

const RegisterTeacher = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [FirstName, setFirstName] = useState('')
  const [LastName, setLastName] = useState('')
  const nav = useNavigate()

  const handleLogin = (e) => {
    e.preventDefault()
    axios.post(`/api/admin/add/teacher`, {
      FirstName: FirstName,
      LastName: LastName,
      email: email,
      password: password
    })
      .then(_ => window.location.reload())
      .catch(e => alert(e))
  }

  const goBack = () => {
    nav("/admin/list")
  }

  return (
    <div className='top_teach_div'>
      <div className='RegisterTeacher'>
        <h2 className='login_text'>Register new teacher</h2>
        <Form>
          <Form.Group controlId="formBasicFirstName">
            <Form.Control
              className='student_input'
              type="text"
              placeholder="Enter teachers first name"
              onChange={e => setFirstName(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicLastName">
            <Form.Control
              className='student_input'
              type="text"
              placeholder="Enter teachers last name"
              onChange={e => setLastName(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicEmail">
            <Form.Control
              className='student_input'
              type="email"
              placeholder="Enter email"
              onChange={e => setEmail(e.target.value)}
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
            Register
          </button>
          <button className="return_button" onClick={goBack}>
            Return
          </button>
        </Form>
      </div>
    </div>
  )
}

export default RegisterTeacher
