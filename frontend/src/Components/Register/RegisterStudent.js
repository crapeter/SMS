import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from "react-router-dom"
import { Form } from 'react-bootstrap';
import axios from 'axios'
import './RegisterStudent.css'

const RegisterStudent = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [FirstName, setFirstName] = useState('')
  const [LastName, setLastName] = useState('')
  const [teacherID, setTeacherID] = useState('')
  const { email } = useParams()
  const nav = useNavigate()

  useEffect(() => {
    axios.get(`/api/teacher/get/id?email=${email}`)
      .then(response => {
        setTeacherID(response.data)
      })
      .catch(e => alert(e))
      // eslint-disable-next-line
  }, [])

  const handleLogin = (e) => {
    e.preventDefault()
    axios.post(`/api/teacher/add/student/${email}`, {
      FirstName: FirstName,
      LastName: LastName,
      username: username,
      password: password
    })
      .then(_ => window.location.reload())
      .catch(e => alert(e))
  }

  const goBack = () => {
    nav(`/teacher/list/${email}/${teacherID}`)
  }

  return (
    <div className='top_stud_div'>
      <div className='RegisterStudent'>
        <h2 className='login_text'>Enroll new student</h2>
        <Form>
          <Form.Group controlId="formBasicFirstName">
            <Form.Control
              className='student_input'
              type="text"
              placeholder="Enter students first name"
              onChange={e => setFirstName(e.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="formBasicLastName">
            <Form.Control
              className='student_input'
              type="text"
              placeholder="Enter students last name"
              onChange={e => setLastName(e.target.value)}
            />
          </Form.Group>

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
            Enroll
          </button>
          <button className="return_button" onClick={goBack}>
            Return
          </button>
        </Form>
      </div>
    </div>
  )
}

export default RegisterStudent

