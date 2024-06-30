import React, { useEffect, useState } from "react"
import { Form } from 'react-bootstrap'
import { useParams, useNavigate } from "react-router-dom"
import './UpdateGrades.css'
import axios from 'axios'

const UpdateGrades = () => {
  const { email, teacherID, username } = useParams()
  const [mathGrade, setMathGrade] = useState("")
  const [elaGrade, setElaGrade] = useState("")
  const [scienceGrade, setScienceGrade] = useState("")
  const [historyGrade, setHistoryGrade] = useState("")
  const [readingGrade, setReadingGrade] = useState("")
  const nav = useNavigate()

  const [student, setStudent] = useState({
    username: '',
    firstName: '',
    lastName: '',
    mathGrade: '',
    elaGrade: '',
    scienceGrade: '',
    historyGrade: '',
    readingGrade: '',
  })

  useEffect(() => {
    fecthStudentInfo()
    // eslint-disable-next-line
  }, [username])

  const fecthStudentInfo = () => {
    axios.get(`/api/student/info/${username}`)
      .then(response => {
        setStudent(response.data)
      })
      .catch(e => alert(e))
  }

  const submitNewGrades = () => {
    let math = mathGrade.trim() === '' ? student.mathGrade : mathGrade
    let ela = elaGrade.trim() === '' ? student.elaGrade : elaGrade
    let sci = scienceGrade.trim() === '' ? student.scienceGrade : scienceGrade
    let his = historyGrade.trim() === '' ? student.historyGrade : historyGrade
    let read = readingGrade.trim() === '' ? student.readingGrade : readingGrade

    axios.post(`/api/teacher/update/grades/${email}/${student.username}`, {
      math: math,
      ela: ela,
      science: sci,
      history: his,
      reading: read
    })
      .then(response => {
        alert(response.data)
        window.location.reload()
      })
      .catch(e => alert(e))
  }

  const goBack = () => {
    nav(`/teacher/update/student/${email}/${teacherID}/${username}`)
  }

  return (
    <div className="the_very_toppest_div">
      <div className="not_the_very_toppest_div">
        <div className="third_top_div">
          <h1>Update Grades</h1>
          <Form className="form">
            <Form.Group className="formGroup" controlId="mathGrade">
              <Form.Label className="formLabel">Math:</Form.Label>
              <Form.Control
                type="text"
                className="formControl"
                placeholder={student.mathGrade}
                value={mathGrade}
                onChange={(e) => setMathGrade(e.target.value)}
              />
            </Form.Group>

            <Form.Group className="formGroup" controlId="elaGrade">
              <Form.Label className="formLabel">ELA:</Form.Label>
              <Form.Control
                type="text"
                className="formControl"
                placeholder={student.elaGrade}
                value={elaGrade}
                onChange={(e) => setElaGrade(e.target.value)}
              />
            </Form.Group>

            <Form.Group className="formGroup" controlId="scienceGrade">
              <Form.Label className="formLabel">Science:</Form.Label>
              <Form.Control
                type="text"
                className="formControl"
                placeholder={student.scienceGrade}
                value={scienceGrade}
                onChange={(e) => setScienceGrade(e.target.value)}
              />
            </Form.Group>

            <Form.Group className="formGroup" controlId="historyGrade">
              <Form.Label className="formLabel">History:</Form.Label>
              <Form.Control
                type="text"
                className="formControl"
                placeholder={student.historyGrade}
                value={historyGrade}
                onChange={(e) => setHistoryGrade(e.target.value)}
              />
            </Form.Group>

            <Form.Group className="formGroup" controlId="readingGrade">
              <Form.Label className="formLabel">Reading:</Form.Label>
              <Form.Control
                type="text"
                className="formControl"
                placeholder={student.readingGrade}
                value={readingGrade}
                onChange={(e) => setReadingGrade(e.target.value)}
              />
            </Form.Group>
          </Form>
          <div className="update_buttons_div">
            <button className="update_buttons" onClick={submitNewGrades}>Update</button>
            <button className="return_button" onClick={goBack}>Return</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default UpdateGrades
