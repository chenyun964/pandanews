import { Component, Fragment } from 'react';
import OrganisationModel from '../model/OrganisationModel';
import UserModel from '../model/UserModel';

class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fields: {},
            errors: {},
            loading: false,
            loginFailed: false
        }
    }

    componentDidMount() {
        UserModel.userOrg().then(res => {
            this.setState({
                company: res.data
            })
        }).catch(e => {
            console.log(e)
        })
    }

    handleChange(field, e) {
        let fields = this.state.fields;
        fields[field] = e.target.value;
        this.setState({ fields });
    }

    handleValidationOrg() {
        let fields = this.state.fields;
        let errors = {};
        let formIsValid = true;

        //Title
        if (!fields["title"]) {
            formIsValid = false;
            errors["title"] = "Title cannot be empty";
        }

        if (typeof fields["title"] !== "undefined") {
            if (!fields["title"].match(/^[a-zA-Z ]+$/)) {
                formIsValid = false;
                errors["title"] = "Only letters and space";
            }
        }

        // Address
        if (!fields["address"]) {
            formIsValid = false;
            errors["address"] = "Address cannot be empty";
        }

        if (typeof fields["address"] !== "undefined") {
            if (!fields["address"].match(/^[A-Za-z0-9 #-]+$/)) {
                formIsValid = false;
                errors["address"] = "Only letters and space";
            }
        }

        // Contact
        if (!fields["contact"]) {
            formIsValid = false;
            errors["contact"] = "Contact cannot be empty";
        }

        if (typeof fields["contact"] !== "undefined") {
            if (!fields["contact"].match(/^[0-9+-]+$/)) {
                formIsValid = false;
                errors["contact"] = "Invalid contact number";
            }
        }

        this.setState({ errors: errors });
        return formIsValid;
    }

    createOrg() {
        this.setState({
            loading: true
        })

        if (this.handleValidationOrg()) {
            OrganisationModel.create(this.state.fields).then(res => {
                this.setState({
                    company: res.data
                })
            }).catch(e => {
                console.log(e);
            })
        }

        this.setState({
            loading: false
        })
    }

    renderOptions() {
        if (this.state.company === "" || this.state.company === undefined || this.state.company == null) {
            return this.renderBothForm();
        } else if (this.state.company.status === 0) {
            return this.renderPending(1);
        } else {
            return this.renderDashboard();
        }
    }

    renderBothForm() {
        return <Fragment>
            <p className="text-center"> You have yet to join an ogranisation, please select on of the option</p>
            <div class="row">
                <div class="col-md-5 offset-md-1 offset-0">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Join an ogranisation</h5>
                            <p class="card-text">Please enter the ogranisation code:</p>
                            <div class="mb-3">
                                <input type="text" class="form-control" placeholder="code" ref="code" onChange={this.handleChange.bind(this, "code")}
                                    value={this.state.fields["code"]} />
                                <span className="input-error-msg">{this.state.errors["code"]}</span>
                            </div>
                            <a href="#" class="btn btn-primary">Join Now</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Create an organisation</h5>
                            <p class="card-text">Set up your own organisation and invite employees to join</p>
                            <div class="mb-3">
                                <input type="text" class="form-control" placeholder="title" ref="title" onChange={this.handleChange.bind(this, "title")}
                                    value={this.state.fields["title"]} />
                                <span className="input-error-msg">{this.state.errors["title"]}</span>
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" placeholder="address" ref="address" onChange={this.handleChange.bind(this, "address")}
                                    value={this.state.fields["address"]} />
                                <span className="input-error-msg">{this.state.errors["address"]}</span>
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" placeholder="contact number" ref="contact" onChange={this.handleChange.bind(this, "contact")}
                                    value={this.state.fields["contact"]} />
                                <span className="input-error-msg">{this.state.errors["contact"]}</span>
                            </div>
                            <button class="btn btn-primary" onClick={() => this.createOrg()}>Create Now</button>
                        </div>
                    </div>
                </div>
            </div>
        </Fragment>
    }

    renderPending() {
        return <Fragment>
            <p className="text-center"> You have yet to join an ogranisation, please select on of the option</p>
            <div class="row">
                <div class="col-md-5 offset-md-1 offset-0">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Join an ogranisation</h5>
                            <p class="card-text">Please enter the ogranisation code:</p>
                            <div class="mb-3">
                                <input type="text" class="form-control" placeholder="code" ref="code" onChange={this.handleChange.bind(this, "code")}
                                    value={this.state.fields["code"]} />
                                <span className="input-error-msg">{this.state.errors["code"]}</span>
                            </div>
                            <a href="#" class="btn btn-primary">Join Now</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Organisation pending for approval </h5>
                            <p class="card-text">Your organisation is waiting for approval, we will get back to you soon!</p>
                            <div className="d-flex flex-column ">
                                <div className="mb-2">Application Details:</div>
                                <div className="mb-2">
                                    <strong>Title: </strong> {this.state.company.title}
                                </div>
                                <div className="mb-2">
                                    <strong>Address: </strong> {this.state.company.address}
                                </div>
                                <div className="mb-2">
                                    <strong>Contact: </strong> {this.state.company.contact}
                                </div>
                                <div className="mb-2">
                                    <strong>Status: </strong> <span className="badge bg-pill bg-warning"> Pending </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Fragment>
    }

    renderDashboard() {
        return <Fragment>
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Ogranisation Name: {this.state.company.title}</h5>
                    <p class="card-text">Contact Number: {this.state.company.contact}</p>
                </div>
            </div>
            <div className="d-flex card-container">
                <div class="card mb-3 flex-fill">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Hi, Test</h5>
                        <div className="d-flex">
                            <div className="flex-fill text-center">
                                <p>Vaccinated</p>
                                <p>Vaccinated or not</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card mb-3 flex-fill">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Timesheet <span>11 Oct 2021</span></h5>
                        <div>
                            <p>Punch in at</p>
                            <p>Wed, 11th Oct 2021 10.00 AM</p>
                        </div>
                        <div classname="text-center">
                            <div class="progress blue">
                                <span class="progress-left">
                                    <span class="progress-bar"></span>
                                </span>
                                <span class="progress-right">
                                    <span class="progress-bar"></span>
                                </span>
                                <div class="progress-value">90%</div>
                            </div>
                        </div>

                        <button className="btn btn-primary mt-3">Punch Out</button>
                        <hr />
                        <div className="d-flex">
                            <div className="flex-fill text-center">
                                <p>BREAK</p>
                                <p>1.21 hrs</p>
                            </div>
                            <div className="flex-fill text-center">
                                <p>Overtime</p>
                                <p>3 hrs</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Fragment>
    }

    render() {
        return (
            <div className="p-5 flex-fill">
                <div className="flex-fill">
                    <h1>Dashboard</h1>
                    {this.renderOptions()}
                </div>
            </div>
        );
    }
}

export default Dashboard;

