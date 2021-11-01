import { Component, Fragment } from 'react';
import { Link } from "react-router-dom";
import LoginModel from "../model/LoginModel";

class TopNav extends Component {

    logout() {
        LoginModel.destoryAll();
        window.location.replace("/login");
    }

    render() {
        return (
            <Fragment>
                <nav class="top-toolbar navbar navbar-mobile navbar-tablet">
                    <ul class="navbar-nav nav-left">
                        <li class="nav-item">
                            <a href="javascript:void(0)" data-toggle-state="aside-left-open">
                                <i class="icon dripicons-align-left"></i>
                            </a>
                        </li>
                    </ul>
                    <ul class="navbar-nav nav-center site-logo">
                        <li>
                            <a href="index.html">
                                <span class="brand-text">Pandanews</span>
                            </a>
                        </li>
                    </ul>
                    <ul class="navbar-nav nav-right">
                        <li class="nav-item">
                            <a href="javascript:void(0)" data-toggle-state="mobile-topbar-toggle">
                                <i class="icon dripicons-dots-3 rotate-90"></i>
                            </a>
                        </li>
                    </ul>
                </nav>
                <nav class="top-toolbar navbar navbar-desktop flex-nowrap">
                    <ul class="navbar-nav nav-right">
                        <li class="nav-item dropdown">
                            <a class="nav-link nav-pill user-avatar" data-toggle="dropdown" href="javascript:void(0)" role="button" aria-haspopup="true" aria-expanded="false">
                                <i class="la la-user"></i>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right dropdown-menu-accout">
                                <div class="dropdown-header pb-3">
                                    <div class="media d-user">
                                        <img class="align-self-center mr-3 w-40 rounded-circle" src="../assets/img/avatars/1.jpg" alt="Profile" />
                                        <div class="media-body">
                                            <h5 class="mt-0 mb-0">Albert Einstein</h5>
                                            <span>support@authenticgoods.co</span>
                                        </div>
                                    </div>
                                </div>
                                <a class="dropdown-item" href="javascript:void(0)" onClick={() => this.logout()}><i class="icon dripicons-lock-open"></i> Sign Out</a>
                            </div>
                        </li>
                    </ul>
                </nav>
            </Fragment>
        );
    }
}

export default TopNav;

