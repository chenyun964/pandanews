import React, { Component, Fragment } from 'react';
import { NavLink } from "react-router-dom";
import LoginModel from '../../model/LoginModel';
import UserModel from '../../model/UserModel';

class Sidebar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authorityLevel: null
    }
  }

  componentDidMount() {
    UserModel.profile().then(res => {
      let role = res.data.authorities[0].authority;
      let level = 0;
      switch (role) {
        case "ROLE_USER":
          level = 1;
          break;
        case "ROLE_MANAGER":
          level = 2;
          break;
        case "ROLE_OWNER":
          level = 3;
          break;
        default:
          level = 0;
      }
      this.setState({
        authorityLevel: level
      })
    }).catch(e => {
      console.log(e)
    })
  }

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
          {this.state.authorityLevel > 1 &&
            <Fragment>
              <p className="section-title">Employee</p>
              <ul className="nav nav-pills flex-column mb-auto section-nav">
                <li className="nav-item" >
                  <NavLink to="/attendance" className="nav-link" ><i class="far fa-check-square"></i><span>Attendance</span></NavLink>
                </li>
              </ul>
            </Fragment>
          }

          {this.state.authorityLevel > 2 &&
            <Fragment>
              <p className="section-title">Manager</p>
              <ul className="nav nav-pills flex-column mb-auto section-nav">
                <li className="nav-item" >
                  <NavLink to="/employee" className="nav-link" ><i class="fas fa-user-friends"></i><span>Employee</span></NavLink>
                </li>
                <li className="nav-item" >
                  <NavLink to="/policy" className="nav-link" ><i class="fas fa-scroll"></i><span>Policy</span></NavLink>
                </li>
                <li className="nav-item" >
                  <NavLink to="/workgroup" className="nav-link" ><i class="fas fa-users"></i><span>Workgroup</span></NavLink>
                </li>
                <li className="nav-item" >
                  <NavLink to="/organisation" className="nav-link" ><i class="fas fa-building"></i><span>Organisation</span></NavLink>
                </li>
              </ul>
            </Fragment>
          }

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