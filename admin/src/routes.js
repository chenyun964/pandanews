import React, { Component, Fragment } from 'react';
import { Route, Switch } from 'react-router';
import LoginModel from './model/LoginModel';

import SideNav from "./module/SideNav";
import TopNav from "./module/TopNav";
import Login from "./module/Login";
import Dashboard from './module/Dashboard';
import Organisation from "./module/Organisation";
import VacciSpotTable from "./module/VacciSpotTable";
import TestSpotTable from './module/TestSpotTable';
import Measurement from "./module/Measurement";
import EditMeasurement from "./module/EditMeasurement";
import News from "./module/News";
import Statistics from "./module/Statistics";
import NewsCategory from './module/NewsCategory';
import EditNews from './module/EditNews';
import Signup from './module/Signup';


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
                    <Route exact path="/" component={Dashboard} />
                    <Route exact path="/dashboard" component={Dashboard} />
                    <Route exact path="/organisation" component={Organisation} />
                    <Route exact path="/vaccispot" component={VacciSpotTable} />
                    <Route exact path="/testspot" component={TestSpotTable} />
                    <Route exact path="/measurement" component={Measurement} />
                    <Route exact path="/measurement/create" component={EditMeasurement} />
                    <Route exact path="/measurement/:id" component={EditMeasurement} />
                    <Route exact path="/news" component={News} />
                    <Route exact path="/news/create" component={EditNews} />
                    <Route exact path="/news/:id" component={EditNews} />
                    <Route exact path="/statistics" component={Statistics} />
                    <Route exact path="/category" component={NewsCategory} />
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
                    <Route exact path="/login" component={Login} />
                    <Route exact path="/signup" component={Signup} />
                    <LoginRoutes />
                </Switch>
            </Fragment>
        );
    }
}

export default AllRoutes;