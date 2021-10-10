import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';
import LoginModel from './model/LoginModel';

import SideNav from "./module/SideNav";
import TopNav from "./module/TopNav";
import Login from "./module/Login";
import Dashboard from './module/Dashboard';
import Organisation from "./module/Organisation";

class LoginRoutes extends Component {
    componentDidMount() {
        if (!LoginModel.retrieveToken()) {
            window.location.replace("/login");
        }
    }

    render() {
        return <Fragment>
            <SideNav />
            <div class="content-wrapper">
                <TopNav />
                <Switch>
                    <Route exact path="/dashboard" component={Dashboard} />
                    <Route exact path="/organisation" component={Organisation} />
                </Switch>
            </div>
        </Fragment>
    }
}

class AllRoutes extends Component {
    render() {
        return (
            <Fragment>
                <Switch>
                    <Route exact path="/news" component={Login} />
                    <Route exact path="/login" component={Login} />
                    <LoginRoutes />
                </Switch>
            </Fragment>
        );
    }
}

export default AllRoutes;