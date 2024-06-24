import React, { useState } from "react"
import { Modal, Button, Form } from 'react-bootstrap'
import axios from 'axios'

const Logout = () => {
  const [show, setShow] = useState(false)
  const [email, setEmail] = useState("")

  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)

  const handleRetire = (event) => {
    event.preventDefault()
    axios.delete(`/teacher/resign?email=${email}`)
      .then(() => {
        alert("Teacher has retired")
        handleClose()
      })
      .catch(e => console.error(e))
  }

  return (
    <div className="retire_button">
      <Button variant="primary"
        onClick={handleShow}
        className="resign_button"
        style={{ margin: '10px' }}
      >
        Resign
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>
            Resign
          </Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <Form onSubmit={handleRetire}>
            <Form.Group controlId="formBasicUsername">
              <Form.Control
                type="email"
                placeholder="Enter your email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <p className="separator">|</p>
          <Button variant="primary" type="submit" onClick={handleRetire}>
            Confirm
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  )
}

export default Logout
