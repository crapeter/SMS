import React, { useState } from "react"
import { Modal, Button, Form } from 'react-bootstrap'
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./Resign.css"

const Logout = () => {
  const [show, setShow] = useState(false)
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const nav = useNavigate()

  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)

  const handleResign = (event) => {
    event.preventDefault()
    axios.delete(`/api/admin/remove/admin?email=${email}&password=${password}`)
      .then(_ => {
        handleClose()
        nav("/")
      })
      .catch(e => alert(e))
  }

  return (
    <div className="login_button">
      <button onClick={handleShow} className="actual_resign_button_for_real">
        Resign
      </button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>
            Resign as an Admin
          </Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <Form onSubmit={handleResign}>
            <Form.Group controlId="formBasicUsername">
              <Form.Control
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="formBasicUsername">
              <Form.Control
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="primary" type="submit" onClick={handleResign}>
            Confirm
          </Button>
          <p className="separator">|</p>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  )
}

export default Logout
