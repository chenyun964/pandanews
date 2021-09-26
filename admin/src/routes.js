import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';
import Dashboard from './module/Dashboard';

import Login from "./module/Login";

class AllRoutes extends Component {
    render() {
        return (
            <Fragment>
                <Switch>
                    <Route exact path="/login" component={Login} />
                    <Route exact path="/dashboard" component={Dashboard} />
                </Switch>
            </Fragment>
        );
    }
}

export default AllRoutes;