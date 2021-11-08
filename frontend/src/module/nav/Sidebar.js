import React, { Component } from 'react';
import { NavLink } from "react-router-dom";
import LoginModel from '../../model/LoginModel';

class Sidebar extends Component {
  logout() {
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
              <NavLink to="/dashboard" className="nav-link" aria-current="page"><i class="fas fa-tachometer-alt"></i><span>Dashboard</span></NavLink>
            </li>
          </ul>

          <p className="section-title">Employee</p>
          <ul className="nav nav-pills flex-column mb-auto section-nav">
            <li className="nav-item" >
              <NavLink to="/schedule" className="nav-link" ><i class="fas fa-users"></i><span>Schedule</span></NavLink>
            </li>
            <li className="nav-item" >
              <NavLink to="/checkin" className="nav-link" ><i class="far fa-check-square"></i><span>Check-in</span></NavLink>
            </li>
          </ul>

          <p className="section-title">Manager</p>
          <ul className="nav nav-pills flex-column mb-auto section-nav">
            <li className="nav-item" >
              <NavLink to="/employee" className="nav-link" ><i class="fas fa-users"></i><span>Employee</span></NavLink>
            </li>
            <li className="nav-item" >
              <NavLink to="/workgroup" className="nav-link" ><i class="fas fa-users"></i><span>Workgroup</span></NavLink>
            </li>
          </ul>

          <p className="section-title">Setting</p>
          <ul className="nav nav-pills flex-column mb-auto section-nav">
            <li className="nav-item" >
              <NavLink to="/profile" className="nav-link"><i class="fas fa-user-alt"></i><span>Profile</span></NavLink>
            </li>
          </ul>
        </div>
        <button className="btn btn-danger mb-2" onClick={() => this.logout()}> Sign out </button>
      </div>
    );
  }
}

export default Sidebar;