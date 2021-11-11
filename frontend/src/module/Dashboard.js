import { Component, Fragment } from 'react';
import OrganisationModel from '../model/OrganisationModel';
import UserModel from '../model/UserModel';
import { Modal, Button, Typography, Result, Popconfirm, Table } from 'antd';
import AttendanceModel from '../model/AttendanceModel';
import moment from "moment";

const { Text } = Typography;

class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profile: {},
            fields: {},
            errors: {},
            company: {},
            attendance: {},
            loading: false,
            loginFailed: false,
            attendanceList: null
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

        UserModel.profile().then(res => {
            this.setState({
                profile: res.data
            })
        }).catch(e => {
            console.log(e)
        })

        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0');
        var yyyy = today.getFullYear();

        today = yyyy + '-' + mm + '-' + dd;
        AttendanceModel.getAttendanceByDate(today).then(res => {
            this.setState({
                attendance: res.data
            });
        }).catch(e => {
            console.log(e)
        })
    }

    punchInOrOut() {
        console.log(1);
        AttendanceModel.markAttendance().then(res => {
            console.log(res.data);
            this.setState({
                attendance: res.data
            });
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
            if (this.state.profile.id && this.state.attendanceList == null) {
                AttendanceModel.getAttendanceByUser(this.state.profile.id).then(res => {
                    this.setState({
                        attendanceList: res.data
                    })
                    return this.renderDashboard()
                }).catch(e => {
                    console.log(e);
                })
            }

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

    info() {
        Modal.confirm({
            title: 'This action is non reversible',
            content: (
                <div>
                    <p>Are you sure you wanto to delete <Text mark>{this.state.company.title}</Text></p>
                </div>
            ),
            okText: "Delete",
            okType: "danger",
            onOk: () => {
                this.confirmDelete();
            }
        });
    }

    confirmDelete() {
        OrganisationModel.delete(this.state.company.id).then(res => {
            window.location.reload();
        }).catch(e => {
            console.log(e)
        })
    }

    updateVaccinated() {
        UserModel.vaccinated().then(res => {
            this.setState({
                profile: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    renderDashboard() {
        const columns = [
            {
                title: 'Date',
                dataIndex: 'punchInDate',
                key: 'punchInDate',
                render: (_, record) => {
                    let date = record.punchInDate[0] + ":" + record.punchInDate[1] + ":" + record.punchInDate[2];
                    return <div>{moment(date, "YYYY-MM-DD").format("YYYY-MM-DD")}</div>
                }
            },
            {
                title: 'Punch In Time',
                dataIndex: 'punchInTime',
                key: 'punchInTime',
                render: (_, record) => {
                    let inTime = record.punchInTime[0] + ":" + record.punchInTime[1];
                    return <div>{moment(inTime, "hh:mm").format("hh:mm")}</div>
                }
            },
            {
                title: 'Punch Out Time',
                dataIndex: 'punchOutTime',
                key: 'punchOutTime',
                render: (_, record) => {
                    if (record.punchOutTime) {
                        let outTime = record.punchOutTime[0] + ":" + record.punchOutTime[1];
                        return <div>{moment(outTime, "hh:mm").format("hh:mm")}</div>
                    }
                }
            },
        ];

        return <Fragment>
            <div className="card-container">
                <div className="row">
                    <div className="col-12 col-lg-6 mb-3">
                        <div class="card flex-fill">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">Hi, {this.state.profile.name ? this.state.profile.name : this.state.profile.username}</h5>
                                <div className="d-flex">
                                    <div className="flex-fill text-center">
                                        {this.state.profile.vaccinated ?
                                            <Result
                                                status="success"
                                                title="Vaccinated"
                                            />
                                            :
                                            <Result
                                                status="warning"
                                                title="Not Vaccinated"
                                                extra={
                                                    <Button type="primary" key="console" onClick={() => this.updateVaccinated()}>
                                                        Vaccinate Now
                                                    </Button>
                                                }
                                            />
                                        }
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="col-12 col-lg-6">
                        <div class="card mb-3 flex-fill">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">Timesheet <span>{moment().format("DD MMM YYYY")}</span></h5>
                                {!this.state.attendance ?
                                    <div className="in-card-container mb-3">
                                        <strong>You have not punch in today</strong>
                                    </div>
                                    :
                                    <div className="in-card-container mb-3">
                                        {!this.state.attendance.punchedOut ?
                                            <div>
                                                <strong>Punch in at</strong><br />
                                                <span>{moment(this.state.attendance.createdAt).format("dddd, Do MMM YYYY LT")}</span>
                                            </div>
                                            :
                                            <div>
                                                <strong>Punch out at</strong><br />
                                                <span>{moment(this.state.attendance.updatedAt).format("dddd, Do MMM YYYY LT")}</span>
                                            </div>
                                        }

                                    </div>
                                }

                                {!this.state.attendance ?
                                    <button className="btn btn-primary mt-3" onClick={() => this.punchInOrOut()}>Punch In</button>
                                    :
                                    <Popconfirm
                                        className={this.state.attendance.punchedOut ? "btn btn-primary mt-3 disabled" : "btn btn-primary mt-3"}
                                        title="Sure to punch out?"
                                        onConfirm={() => this.punchInOrOut()}
                                        disabled={this.state.attendance.punchedOut}>
                                        Punch Out
                                    </Popconfirm>
                                }
                            </div>
                        </div>
                    </div>
                    <div className="col-12">
                        <div className="card">
                            <h5 className="p-3">Attendance List</h5>
                            <Table columns={columns} dataSource={this.state.attendanceList} />
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

