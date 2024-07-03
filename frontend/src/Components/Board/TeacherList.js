import React, { useEffect, useState } from "react"
import { Modal, Button, Form } from 'react-bootstrap'
import { useAuth } from "../Misc/AuthContext"
import Logout from "../Misc/Logout"
import Resign from "./Resign"
import RegisterTeacher from "../Misc/ToRegisterTeacher"
import axios from "axios"
import './TeacherList.css'

const TeacherList = ({ email, password }) => {
  const { isBoard } = useAuth()
  const [show, setShow] = useState(false)
  const [teachers, setTeacher] = useState([])
  const [selectedTeacher, setSelectedTeacher] = useState(null);
  const [replacementEmail, setReplacementEmail] = useState('');

  const handleClose = () => setShow(false)

  useEffect(() => {
    getTeachers()
  }, [])

  const getTeachers = () => {
    axios.get(`/api/admin/get/teachers`)
      .then(response => {
        setTeacher(response.data)
      })
      .catch(e => alert(e))
  }

  const handleDelete = () => {
    if (selectedTeacher.numOfStudents === 0) {
      axios.delete(`/api/admin/remove?email=${selectedTeacher.email}&password=${selectedTeacher.password}`)
        .then(_ => {
          setSelectedTeacher(null);
          window.location.reload()
        })
        .catch(e => alert(e))
    } else if (replacementEmail) {
      axios.delete(`/api/admin/move/class?oldTeacherEmail=${selectedTeacher.email}&newTeacherEmail=${replacementEmail}`)
        .then(_ => {
          setSelectedTeacher(null)
          window.location.reload()
        })
        .catch(e => alert(e));
    } else {
      setSelectedTeacher(null);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    handleDelete();
    setShow(false)
  }

  const handleClick = (teacher) => {
    setSelectedTeacher(teacher);
    setShow(true);
  }

  return (
    <div>
      {isBoard ? (
        <div className='we_da_top_row custome-style'>
          <div className='top_row'>
            <RegisterTeacher />
            <Logout />
          </div>
          <Resign email={email} password={password} />
          <div className='students_i_guess'>
            <h1>Teachers</h1>
            <ul className='stud_list'>
              {teachers.map(teacher => (
                <li key={teacher._id} className='teacher_items'>
                  <img
                    src={`${process.env.PUBLIC_URL}/trash-can.svg`}
                    alt="Delete"
                    onClick={() => handleClick(teacher)}
                    style={{ cursor: 'pointer', margin: '5px', width: '20px', height: '20px' }}
                  />
                  <p>
                    {teacher.firstName} {teacher.lastName}, {teacher.numOfStudents}{" "}
                    {teacher.numOfStudents === 1 ? "student" : "students"} currently enrolled
                  </p>
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

      {selectedTeacher && (
        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>
              Fire {selectedTeacher.firstName} {selectedTeacher.lastName}
            </Modal.Title>
          </Modal.Header>

          <Modal.Body>
            <Form onSubmit={handleSubmit}>
              <Form.Group controlId="formBasicUsername">
                <Form.Control
                  type="email"
                  placeholder="Replace Email (non required if they don't have students)"
                  value={replacementEmail}
                  onChange={(e) => setReplacementEmail(e.target.value)}
                />
              </Form.Group>
            </Form>
          </Modal.Body>

          <Modal.Footer>
            <Button variant="primary" type="submit" onClick={handleSubmit}>
              Confirm
            </Button>
            <p className="separator">|</p>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      )}
    </div>
  )
}

export default TeacherList
