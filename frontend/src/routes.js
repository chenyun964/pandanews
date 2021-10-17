import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';
import LoginModel from './model/LoginModel';

import Sidebar from "./module/nav/Sidebar";
import Nav from "./module/nav/Nav";
import Login from "./module/Login";
import Signup from './module/Signup';
import Dashboard from './module/Dashboard';
import Home from './module/Home';
import Category from './module/Category';
import Profile from "./module/Profile";
import NotFound from "./module/NotFound";
import Employee from './module/Employee';
import Invite from './module/Invite';
import Measurement from "./module/Measurement";
import MeaAdminTable from "./module/MeaAdminTable";
import VacciSpotTabs from './module/VacciSpotTabs';
import VacciSpotAdminTable from './module/VacciSpotAdminTable';


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
          <Route exact path="/" component={Home} />
          <Route exact path="/category/:category" component={Category} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/signup" component={Signup} />
          <Route exact path="/employee/invite" component={Invite} />
          <Route exact path="/map" component={Map} />
          <Route exact path="/measurements" component={Measurement} />
          <Route exact path="/measurements/admin" component={MeaAdminTable} />

          {/* Add your routes above this */}
          {!LoginModel.retrieveToken() &&
            <Route path="*" component={NotFound} />
          }

          <Route exact path="/vaccispots" component={VacciSpotTabs} />
          <Route exact path="/vaccispotsadmin" component={VacciSpotAdminTable} />
          {LoginModel.retrieveToken() &&
            <LoginRoutes />
          }
          {/* Add your routes above this */}
          <Route path="*" component={NotFound} />
        </Switch>
      </Fragment>
    );
  }
}

export default AllRoutes;