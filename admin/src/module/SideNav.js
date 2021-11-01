import { Component, Fragment } from 'react';
import { Link } from "react-router-dom";
import OrganisationModel from "../model/OrganisationModel";

class SideNav extends Component {

    render() {
        return (
            <Fragment>
                <aside class="sidebar sidebar-left">
                    <div class="sidebar-content">
                        <div class="aside-toolbar">
                            <ul class="site-logo">
                                <li>
                                    <a href="/">
                                        <span class="brand-text">PandaNews</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <nav class="main-menu">
                            <ul class="nav metismenu">
                                <li class="sidebar-header"><span>NAVIGATION</span></li>
                                <li><a href="/dashboard"><i class="icon dripicons-meter"></i><span>Dashboard</span></a></li>
                                <li><a href="/organisation"><i class="la la-building"></i><span>Organisation</span></a></li>
                                <li><a href="/vaccispot"><i class="fas fa-syringe"></i><span>Vaccination Spots</span></a></li>
                                <li><a href="/testspot"><i class="fas fa-swatchbook"></i><span>Swab Test Spots</span></a></li>
                                <li><a href="/measurement"><i class="fas fa-chart-bar"></i><span>Measurements</span></a></li>
                            </ul>
                        </nav>
                    </div>
                </aside>
            </Fragment>
        );
    }
}

export default SideNav;

