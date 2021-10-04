import React, { Component } from 'react';
import { Link } from "react-router-dom";
import LoginModel from '../../model/LoginModel';

class Sidebar extends Component {
  logout(){
    LoginModel.destoryAll();
    window.location.replace("/login");
  }

  render() {
    return (
      <div id="login-side-nav" className="d-flex flex-column justify-content-between">
        <div>
          <p className="section-title">Main</p>
          <ul className="nav nav-pills flex-column mb-auto section-nav">
            <li className="nav-item" >
              <Link to="/dashboard" className="nav-link active" aria-current="page"><i class="fas fa-tachometer-alt"></i><span>Dashboard</span></Link>
            </li>
          </ul>

          <p className="section-title">Employee</p>
          <ul className="nav nav-pills flex-column mb-auto section-nav">
            <li className="nav-item" >
              <Link to="/dashboard" className="nav-link" ><i class="fas fa-users"></i><span>Schedule</span></Link>
            </li>
            <li className="nav-item" >
              <Link to="/dashboard" className="nav-link" ><i class="far fa-check-square"></i><span>Check-in</span></Link>
            </li>
          </ul>

          <p className="section-title">Manager</p>
          <ul className="nav nav-pills flex-column mb-auto section-nav">
            <li className="nav-item" >
              <Link to="/dashboard" className="nav-link" ><i class="fas fa-users"></i><span>Workgroup</span></Link>
            </li>
          </ul>

          <p className="section-title">Setting</p>
          <ul className="nav nav-pills flex-column mb-auto section-nav">
            <li className="nav-item" >
              <Link to="/dashboard" className="nav-link"><i class="fas fa-user-alt"></i><span>Profile</span></Link>
            </li>
            <li className="nav-item" >
              <Link to="/dashboard" className="nav-link"><i class="fas fa-cog"></i><span>Preference</span></Link>
            </li>
          </ul>
        </div>
        <button className="btn btn-danger mb-2" onClick={() => this.logout()}> Sign out </button>
      </div>
    );
  }
}

export default Sidebar;