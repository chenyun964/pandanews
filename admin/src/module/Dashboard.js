import { Component } from 'react';
import UserModel from '../model/UserModel';
import StatisticsModel from '../model/StatisticsModel';

class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profile: {},
            summary: {}
        }
    }

    componentDidMount() {
        this.getProfile();
        this.getSummary();
    }

    getProfile() {
        UserModel.profile().then(res => {
            this.setState({
                profile: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    getSummary() {
        StatisticsModel.summary().then(res => {
            this.setState({
                summary: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    render() {
        return (
            <div className="content">
                <header className="page-header">
                    <div className="d-flex align-items-center">
                        <div className="mr-auto">
                            <h1>Dashboard</h1>
                        </div>
                    </div>
                </header>

                <section className="page-content container-fluid">
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="card">
                                <div className="card-header">
                                    <span className="d-inline-block">Hi, {this.state.profile.username}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="card">
                        <div className="card-header">
                            <h5 className="d-inline-block">Covid Statistics</h5>
                        </div>
                        <div class="row m-0 col-border-xl">
                            <div class="col-md-12 col-lg-6 col-xl-4">
                                <div class="card-body">
                                    <div class="icon-rounded icon-rounded-accent float-left m-r-20"></div>
                                    <h5 class="card-title m-b-5 counter">{this.state.summary.totalCases}</h5>
                                    <h6 class="text-muted m-t-10">
                                        Total Cases
                                    </h6>
                                </div>
                            </div>
                            <div class="col-md-12 col-lg-6 col-xl-4">
                                <div class="card-body">
                                    <div class="icon-rounded icon-rounded-success float-left m-r-20"></div>
                                    <h5 class="card-title m-b-5 counter">{this.state.summary.totalRecovery}</h5>
                                    <h6 class="text-muted m-t-10">
                                        Total Recovery
                                    </h6>
                                </div>
                            </div>
                            <div class="col-md-12 col-lg-6 col-xl-4">
                                <div class="card-body">
                                    <div class="icon-rounded icon-rounded-info float-left m-r-20"></div>
                                    <h5 class="card-title m-b-5 counter">{this.state.summary.totalDeath}</h5>
                                    <h6 class="text-muted m-t-10">
                                        Total Death
                                    </h6>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        );
    }
}

export default Dashboard;

