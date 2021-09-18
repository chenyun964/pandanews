import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';

import Nav from "./modules/nav";
import Demo from "./modules/demo";
import Login from "./modules/login";
import Signup from './modules/signup';

class AllRoutes extends Component {
  render() {
    return (
      <Fragment>
        <Nav/>
        <Switch>
          <Route exact path="/" component={Demo} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/signup" component={Signup} />
        </Switch>
      </Fragment>
    );
  }
}

export default AllRoutes;