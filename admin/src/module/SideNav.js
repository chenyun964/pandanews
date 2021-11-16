import { Component, Fragment } from 'react';

class SideNav extends Component {

    render() {
        return (
            <Fragment>
                <aside className="sidebar sidebar-left">
                    <div className="sidebar-content">
                        <div className="aside-toolbar">
                            <ul className="site-logo">
                                <li>
                                    <a href="/">
                                        <span className="brand-text">PandaNews</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <nav className="main-menu">
                            <ul className="nav metismenu">
                                <li className="sidebar-header"><span>NAVIGATION</span></li>
                                <li><a href="/dashboard"><i className="icon dripicons-meter"></i><span>Dashboard</span></a></li>
                                <li><a href="/organisation"><i className="la la-building"></i><span>Organisation</span></a></li>

                                <li className="sidebar-header"><span>NEWS</span></li>
                                <li><a href="/news"><i className="la la-newspaper-o"></i><span>News</span></a></li>
                                <li><a href="/category"><i className="fas fa-list"></i><span>News Category</span></a></li>

                                <li className="sidebar-header"><span>Covid STATISTIC</span></li>
                                <li><a href="/measurement"><i className="la la-tasks"></i><span>Measurements</span></a></li>
                                <li><a href="/statistics"><i className="fas fa-chart-bar"></i><span>Covid Cases</span></a></li>

                                <li className="sidebar-header"><span>COVID SUPPORT</span></li>
                                <li><a href="/vaccispot"><i className="fas fa-syringe"></i><span>Vaccination Spots</span></a></li>
                                <li><a href="/testspot"><i className="fas fa-swatchbook"></i><span>Swab Test Spots</span></a></li>
                            </ul>
                        </nav>
                    </div>
                </aside>
            </Fragment>
        );
    }
}

export default SideNav;

