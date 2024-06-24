import React from "react"
import Login from "./Login"
import RegisterTeacher from "./ToRegisterTeacher"
import "./HomePage.css"

const HomePage = () => {
  return (
    <div className="container">
      <div className="its_flexing_time">
        <RegisterTeacher />
        <Login />
      </div>
      <div className="info_text">
        <h1>Instructions on how this thing works</h1>
        <ul>
          <h3>To be added at a later date as all this is is just some text</h3>
        </ul>
      </div>
    </div>
  )
}

export default HomePage
