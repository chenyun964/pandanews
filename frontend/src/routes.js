import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';

import Nav from "./modules/nav";
import Demo from "./modules/demo";
import Login from "./modules/login";

class AllRoutes extends Component {
  render() {
    return (
      <Fragment>
        <Nav/>
        <Switch>
          <Route exact path="/" component={Demo} />
          <Route exact path="/login" component={Login} />
        </Switch>
      </Fragment>
    );
  }
}

export default AllRoutes;