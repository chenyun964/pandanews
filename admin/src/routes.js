import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';

import Login from "./module/Login";

class AllRoutes extends Component {
    render() {
        return (
            <Fragment>
                <Switch>
                    <Route exact path="/login" component={Login} />
                </Switch>
            </Fragment>
        );
    }
}

export default AllRoutes;