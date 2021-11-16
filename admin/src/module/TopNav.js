import { Component, Fragment } from 'react';
import { Link } from "react-router-dom";
import LoginModel from "../model/LoginModel";
import UserModel from '../model/UserModel';

class TopNav extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profile: {}
        }
    }

    componentDidMount(){
        UserModel.profile().then(res => {
            this.setState({
                profile: res.data
            })
        })
    }


    logout() {
        LoginModel.destoryAll();
        window.location.replace("/login");
    }

    render() {
        return (
            <Fragment>
                <nav className="top-toolbar navbar navbar-mobile navbar-tablet">
                    <ul className="navbar-nav nav-left">
                        <li className="nav-item">
                            <a href="javascript:void(0)" data-toggle-state="aside-left-open">
                                <i className="icon dripicons-align-left"></i>
                            </a>
                        </li>
                    </ul>
                    <ul className="navbar-nav nav-center site-logo">
                        <li>
                            <Link to="/">
                                <span className="brand-text">Pandanews</span>
                            </Link>
                        </li>
                    </ul>
                    <ul className="navbar-nav nav-right">
                        <li className="nav-item">
                            <a href="javascript:void(0)" data-toggle-state="mobile-topbar-toggle">
                                <i className="icon dripicons-dots-3 rotate-90"></i>
                            </a>
                        </li>
                    </ul>
                </nav>
                <nav className="top-toolbar navbar navbar-desktop flex-nowrap">
                    <ul className="navbar-nav nav-right">
                        <li className="nav-item dropdown">
                            <a className="nav-link nav-pill user-avatar" data-toggle="dropdown" href="javascript:void(0)" role="button" aria-haspopup="true" aria-expanded="false">
                                <i className="la la-user"></i>
                            </a>
                            <div className="dropdown-menu dropdown-menu-right dropdown-menu-accout">
                                <div className="dropdown-header pb-3">
                                    <div className="media d-user">
                                        <div className="media-body">
                                            <h5 className="mt-0 mb-0">{this.state.profile.username}</h5>
                                            <span>{this.state.profile.email}</span>
                                        </div>
                                    </div>
                                </div>
                                <a className="dropdown-item" href="javascript:void(0)" onClick={() => this.logout()}><i className="icon dripicons-lock-open"></i> Sign Out</a>
                            </div>
                        </li>
                    </ul>
                </nav>
            </Fragment>
        );
    }
}

export default TopNav;

