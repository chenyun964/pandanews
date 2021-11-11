import { Component } from 'react';
import { Link } from 'react-router-dom';
import LoginModel from "../model/LoginModel";
import { Space, Spin, Result, Button } from 'antd';

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fields: {
                "username": "",
                "password": ""
            },
            errors: {},
            loading: false,
            signupFailed: false,
            signupSuccess: false
        }
    }

    handleValidation() {
        let fields = this.state.fields;
        let errors = {};
        let formIsValid = true;

        //Admin Code
        if (!fields["adminCode"]) {
            formIsValid = false;
            errors["adminCode"] = "Admin Code cannot be empty";
        }

        //Name
        if (!fields["username"]) {
            formIsValid = false;
            errors["username"] = "Username cannot be empty";
        }

        if (typeof fields["username"] !== "undefined") {
            if (!fields["username"].match(/^[a-zA-Z]+$/)) {
                formIsValid = false;
                errors["username"] = "Only letters";
            }
        }

        // Password
        if (!fields["password"]) {
            formIsValid = false;
            errors["password"] = "password cannot be empty";
        }

        if (typeof fields["password"] !== "undefined") {
            if (fields["password"].length < 3) {
                formIsValid = false;
                errors["password"] = "Password is too short";
            }
        }

        if (!fields["confirmPassword"]) {
            formIsValid = false;
            errors["confirmPassword"] = "Confirm password cannot be empty";
        }

        if (typeof fields["confirmPassword"] !== "undefined") {
            if (fields["confirmPassword"] != fields["password"]) {
                formIsValid = false;
                errors["confirmPassword"] = "Password doesn't match";
            }
        }

        this.setState({ errors: errors });
        return formIsValid;
    }

    handleChange(field, e) {
        let fields = this.state.fields;
        fields[field] = e.target.value;
        this.setState({ fields });
    }

    signup() {
        this.setState({
            loading: true,
            signupFailed: false
        })
        if (this.handleValidation()) {
            LoginModel.register(this.state.fields).then(res => {
                this.setState({
                    signupSuccess: true
                })
            }).catch(e => {
                if (e.response && e.response.status == 400) {
                    this.setState({
                        signupFailed: true,
                        failedMsg: e.response.data
                    })
                }
            })
        }
        this.setState({
            loading: false
        })
    }

    render() {
        return (
            <div className="container">
                <div className="sign-in-form">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="sign-in-heading text-center m-b-20">PandaNews Admin Signup</h5>
                            {this.state.signupFailed &&
                                <div className="mb-1 text-center">
                                    <span className="input-error-msg">{this.state.failedMsg}</span>
                                </div>
                            }
                            {this.state.loading &&
                                <Space size="middle">
                                    <Spin size="large" />
                                    <div>Signing Up...</div>
                                </Space>
                            }
                            {this.state.signupSuccess &&
                                <Result
                                    status="success"
                                    title="Sign Up Success"
                                    extra={[
                                        <Link className="btn btn-primary btn-rounded" to="/login">Login Now</Link>,
                                    ]}
                                />
                            }
                            {!this.state.signupSuccess &&
                                <div>
                                    <div className="d-flex flex-column p-5">
                                        <div class="mb-3">
                                            <input type="text" class="form-control" placeholder="Username" ref="username" onChange={this.handleChange.bind(this, "username")}
                                                value={this.state.fields["username"]} />
                                            <span className="input-error-msg">{this.state.errors["username"]}</span>
                                        </div>
                                        <div class="mb-3">
                                            <input type="password" class="form-control" placeholder="Password" ref="password" onChange={this.handleChange.bind(this, "password")}
                                                value={this.state.fields["password"]} />
                                            <span className="input-error-msg">{this.state.errors["password"]}</span>
                                        </div>
                                        <div class="mb-3">
                                            <input type="password" class="form-control" placeholder="Confirm Password" ref="confirmPassword" onChange={this.handleChange.bind(this, "confirmPassword")}
                                                value={this.state.fields["confirmPassword"]} />
                                            <span className="input-error-msg">{this.state.errors["confirmPassword"]}</span>
                                        </div>
                                        <div class="mb-3">
                                            <input type="password" class="form-control" placeholder="Admin Code" ref="adminCode" onChange={this.handleChange.bind(this, "adminCode")}
                                                value={this.state.fields["adminCode"]} />
                                            <span className="input-error-msg">{this.state.errors["adminCode"]}</span>
                                        </div>
                                    </div>
                                    <button className="btn btn-primary btn-rounded btn-floating btn-lg btn-block" onClick={() => this.signup()}>Sign Up</button>
                                    <div className="text-center">
                                        <p class="text-muted m-t-25 m-b-0 p-0">Already have an account?
                                            <Link to="/login"> Login</Link>
                                        </p>
                                    </div>
                                </div>
                            }
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Signup;

