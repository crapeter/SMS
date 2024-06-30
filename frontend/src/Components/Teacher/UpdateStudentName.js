import React, { useState } from "react"
import { Button, Modal, Form } from 'react-bootstrap'
import axios from 'axios'

const UpdateStudentName = ({ fName, lName, email, username }) => {
  const [show, setShow] = useState(false)
  const [firstName, setFirstName] = useState("")
  const [lastName, setLastName] = useState("")

  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)

  const submitNewName = () => {
    let nfName = firstName.trim() === '' ? fName : firstName
    let nlName = lastName.trim() === '' ? lName : lastName

    axios.post(`/api/student/update/name/${email}/${username}/${nfName}/${nlName}`)
      .then(_ => {
        window.location.reload()
        handleClose()
      })
      .catch(e => alert(e))
  }

  return (
    <div className="the_not_very_toppest_div">
      <button
        variant="primary"
        className="update_stud_button"
        onClick={handleShow}
      >
        Change Name
      </button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>
            Update Name
          </Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <Form>
            <Form.Group controlId="formBasicFirstName" className="control_names_group">
              <Form.Control
                className="control_names"
                type="text"
                placeholder={fName}
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="formBasicLastName" className="control_names_group">
              <Form.Control
                className="control_names"
                type="text"
                placeholder={lName}
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="primary" onClick={submitNewName}>Confirm</Button>
          <p className="separator">|</p>
          <Button variant="secondary" onClick={handleClose}>Close</Button>
        </Modal.Footer>
      </Modal>
    </div>
  )
}

export default UpdateStudentName
