import React from "react"
import Login from "./Login"
import "./HomePage.css"

const HomePage = () => {
  return (
    <div className="container">
      <div className="its_flexing_time">
        <Login />
      </div>
      <div className="second_container">
        <div className="info_text">
          <h1>Instructions on how this thing works</h1>
          <p>There are two buttons above that will take you to different locations to log in.</p>
          <p>The student login will only allow for you to see the students current grades and nothing else.</p>
          <p>Compare this to the teacher login where you can add a student/remove a student/or update a students grade</p>
          <p>There is a basic form of authentication and authorization set up. This is because I made the mistake of allowing students to be able to switch to a teacher's page if they new enough information</p>
          <p>This was changed by adding a couple levels of authorization to the program to prevent anything like that from happening.</p>
          <p>If someone tries to access something without being logged in/not have the correct authorization, they will be met with a simple div stating that 'You are not logged in'</p>
        </div>
      </div>
    </div>
  )
}

export default HomePage
