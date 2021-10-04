import { Component } from 'react';
import { Link } from "react-router-dom";
import LoginModel from '../model/LoginModel';

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fields: {
                "username": "",
                "email": "",
                "password": "",
                "confirmPassword": ""
            },
            errors: {},
            registered: false,
            loading: false
        }
    }

    handleValidation() {
        let fields = this.state.fields;
        let errors = {};
        let formIsValid = true;

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

        //Email
        if (!fields["email"]) {
            formIsValid = false;
            errors["email"] = "Email cannot be empty";
        }

        if (typeof fields["email"] !== "undefined") {
            let lastAtPos = fields["email"].lastIndexOf("@");
            let lastDotPos = fields["email"].lastIndexOf(".");

            if (
                !(
                    lastAtPos < lastDotPos &&
                    lastAtPos > 0 &&
                    fields["email"].indexOf("@@") == -1 &&
                    lastDotPos > 2 &&
                    fields["email"].length - lastDotPos > 2
                )
            ) {
                formIsValid = false;
                errors["email"] = "Email is not valid";
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
            loading: true
        })
        if (this.handleValidation()) {
            LoginModel.register(this.state.fields).then(res => {
                this.setState({
                    registered: true
                })
            }).catch(e => {
                console.log(e);
            })
        }
        this.setState({
            loading: false
        })
    }

    renderForm() {
        return this.state.registered ?
            <div className="d-flex align-items-center justify-content-center p-2 text-center flex-column" style={{ "height": "100%" }}>
                <p>Congratulation, your account has been successfully created!</p>
                <Link to="/login">Login Now</Link>
            </div>
            :
            <div className="d-flex flex-column p-5">
                <div className="text-center">
                    <h1 className="section-title pt-2 pb-4">SIGN UP</h1>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" placeholder="Username" ref="username" onChange={this.handleChange.bind(this, "username")}
                        value={this.state.fields["username"]} />
                    <span className="input-error-msg">{this.state.errors["username"]}</span>
                </div>
                <div class="mb-3">
                    <input type="email" class="form-control" placeholder="Email" ref="email" onChange={this.handleChange.bind(this, "email")}
                        value={this.state.fields["email"]} />
                    <span className="input-error-msg">{this.state.errors["email"]}</span>
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
                <div className="text-center">
                    <button className="btn btn-primary" onClick={() => this.signup()}>Sign Up</button>
                </div>
                <hr />
                <p>Already have an account? <Link to="/login">Login Now</Link></p>
            </div>
    }

    render() {
        return (
            <div className="login-section">
                <div class="d-flex align-items-center">
                    <div class="container login-container">
                        <div class="row">
                            <div className="col-12 col-md-6 login-right">
                                {this.state.loading ?
                                    <div className="d-flex align-items-center justify-content-center" style={{ "height": "100%" }}>
                                        <div class="spinner-border text-primary" role="status">
                                            <span class="sr-only">Loading...</span>
                                        </div>
                                    </div>
                                    :
                                    this.renderForm()
                                }
                            </div>
                            <div className="col-12 col-md-6 login-left">
                                <div className="d-flex align-items-center" style={{ "height": "100%" }}>
                                    <img src="../img/login-img.png" />
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

        );
    }

}

export default Signup;

