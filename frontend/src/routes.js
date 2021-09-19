import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';

import Nav from "./module/Nav";
import Demo from "./module/Demo";
import Login from "./module/Login";
import Signup from './module/Signup';
import Dashboard from './module/Dashboard';

class AllRoutes extends Component {
  render() {
    return (
      <Fragment>
        <Nav/>
        <Switch>
          <Route exact path="/" component={Demo} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/signup" component={Signup} />
          <Route exact path="/dashboard" component={Dashboard} />
        </Switch>
      </Fragment>
    );
  }
}

export default AllRoutes;