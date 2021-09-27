import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';
import LoginModel from './model/LoginModel';

import Sidebar from "./module/nav/Sidebar";
import Nav from "./module/nav/Nav";
import Demo from "./module/Demo";
import Login from "./module/Login";
import Signup from './module/Signup';
import Dashboard from './module/Dashboard';

class LoginRoutes extends Component {
  componentDidMount() {
    if (!LoginModel.retrieveToken()) {
      window.location.replace("/login");
    }
  }
  
  render() {
    return (
      <Fragment>
        <Switch>
          <div className="d-flex">
            <Sidebar />
            <Route exact path="/dashboard" component={Dashboard} />
          </div>
        </Switch>
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
          <LoginRoutes />
        </Switch>
      </Fragment>
    );
  }
}

export default AllRoutes;