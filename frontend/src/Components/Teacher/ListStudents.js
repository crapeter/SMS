import React, { useEffect, useState } from 'react';
import { Link, useParams } from "react-router-dom"
import { useAuth } from "../Misc/AuthContext"
import RegisterStudent from '../Misc/ToRegisterStudent'
import Logout from '../Misc/Logout'
import './ListStudents.css'
import axios from 'axios'

const ListStudents = () => {
  const [students, setStudents] = useState([])
  const { email, teacherID } = useParams()
  const { isLoggedIn, isAdmin } = useAuth()

  useEffect(() => {
    getStudents()
  }, [])

  const getStudents = () => {
    axios.get(`/teacher/get/students/${email}`)
      .then(response => {
        setStudents(response.data)
      })
      .catch(e => alert(e))
  }

  const handleDelete = (username) => {
    axios.delete(`/teacher/remove/student?teacherEmail=${email}&studentUsername=${username}`)
      .then(_ => {
        window.location.reload()
      })
      .catch(e => alert(e))
  }

  return (
    <div>
      {isLoggedIn && isAdmin ? (
        <div className='we_da_top_row custome-style'>
          <div className='top_row'>
            <RegisterStudent email={email} />
            <Logout />
          </div>
          <div className='students_i_guess'>
            <h1>Students</h1>
            <ul className='stud_list'>
              {students.map(student => (
                <li key={student._id}>
                  <img
                    src={`${process.env.PUBLIC_URL}/trash-can.svg`}
                    alt="Delete"
                    onClick={() => handleDelete(student.username)}
                    style={{ cursor: 'pointer', margin: '5px', width: '20px', height: '20px' }}
                  />
                  <Link to={`/teacher/update/student/${email}/${teacherID}/${student.username}`}>{student.firstName} {student.lastName}</Link>
                </li>
              ))}
            </ul>
          </div>
        </div>
      ) : (
        <div>
          <h1>You ain't logged in</h1>
        </div>
      )}
    </div>
  )
}

export default ListStudents