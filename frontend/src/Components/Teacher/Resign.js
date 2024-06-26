import React, { useState } from "react"
import { useNavigate } from "react-router-dom"
import { Modal, Button, Form } from 'react-bootstrap'
import axios from 'axios'
import './Resign.css'

const Logout = () => {
  const [show, setShow] = useState(false)
  const [email, setEmail] = useState("")
  const nav = useNavigate()

  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)

  const handleRetire = (event) => {
    event.preventDefault()
    axios.delete(`/teacher/resign/${email}`)
      .then(response => {
        handleClose()
        if (response.data === true) {
          nav(`/`)
        } else {
          alert("Teacher can't resign while still having students")
          window.location.reload()
        }
      })
      .catch(e => console.error(e))
  }

  return (
    <div className="retire_button">
      <button
        onClick={handleShow}
        className="resign_button"
        style={{ margin: '10px' }}
      >
        Resign
      </button>

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
