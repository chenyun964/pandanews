import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';
import LoginModel from './model/LoginModel';

import Sidebar from "./module/nav/Sidebar";
import Nav from "./module/nav/Nav";
import Demo from "./module/Demo";
import Login from "./module/Login";
import Signup from './module/Signup';
import Dashboard from './module/Dashboard';
import Profile from "./module/Profile";
import NotFound from "./module/NotFound";
import Employee from './module/Employee';
import Invite from './module/Invite';
import Measurement from "./module/Measurement";

class LoginRoutes extends Component {
  componentDidMount() {
    if (!LoginModel.retrieveToken()) {
      window.location.replace("/login");
    }
  }

  render() {
    return (
      <Fragment>
        <div className="d-flex">
          <Sidebar />
          <Switch>
            <Route exact path="/dashboard" component={Dashboard} />
            <Route exact path="/profile" component={Profile} />
            <Route exact path="/employee" component={Employee} />
            <Route path="*" component={NotFound} />
          </Switch>
        </div>
      </Fragment>
    );
  }
}

class AllRoutes extends Component {
  render() {
    return (
      <Fragment>
        <Nav />
        <Switch>
          <Route exact path="/" component={Demo} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/signup" component={Signup} />
          <Route exact path="/employee/invite" component={Invite} />
          {!LoginModel.retrieveToken() &&
            <Route path="*" component={NotFound} />
          }
          <Route exact path="/measurements" component={Measurement} />
          <LoginRoutes />
        </Switch>
      </Fragment>
    );
  }
}

export default AllRoutes;