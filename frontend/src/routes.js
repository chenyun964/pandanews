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
import VacciSpotTabs from './module/VacciSpotTabs';
import SearchNews from './module/SearchNews';
import TestSpotTabs from './module/TestSpotTabs';
import Covid19 from './module/Covid19';
import WorkGroup from './module/WorkGroup';
import Attendance from './module/Attendance';
import Policy from './module/Policy';
import Organisation from './module/Organisation';

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
            <Route exact path="/workgroup" component={WorkGroup} />
            <Route exact path="/attendance" component={Attendance} />
            <Route exact path="/policy" component={Policy} />
            <Route exact path="/organisation" component={Organisation} />
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
          <Route exact path="/search/:keyword" component={SearchNews} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/signup" component={Signup} />
          <Route exact path="/employee/invite" component={Invite} />
          <Route exact path="/map" component={Map} />
          <Route exact path="/measurements" component={Measurement} />
          <Route exact path="/vaccispots" component={VacciSpotTabs} />
          <Route exact path="/testspots" component={TestSpotTabs} />
          <Route exact path="/covid19" component={Covid19} />

          {/* Add your routes above this */}
          {!LoginModel.retrieveToken() &&
            <Route path="*" component={NotFound} />
          }

          {LoginModel.retrieveToken() &&
            <LoginRoutes />
          }

          <Route path="*" component={NotFound} />
        </Switch>
      </Fragment>
    );
  }
}

export default AllRoutes;