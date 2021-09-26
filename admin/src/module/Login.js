import { Component } from 'react';
import { Link } from "react-router-dom";
import LoginModel from "../model/LoginModel";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fields: {
                "username": "",
                "password": ""
            },
            errors: {},
            loading: false,
            loginFailed: false
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

        // Password
        if (!fields["password"]) {
            formIsValid = false;
            errors["password"] = "password cannot be empty";
        }

        this.setState({ errors: errors });
        return formIsValid;
    }

    handleChange(field, e) {
        let fields = this.state.fields;
        fields[field] = e.target.value;
        this.setState({ fields });
    }

    login() {
        this.setState({
            loading: true
        })
        if (this.handleValidation()) {
            LoginModel.authenticate(this.state.fields).then(res => {
                this.storeToken(res.data);
                this.props.history.push("/dashboard");
            }).catch(e => {
                this.setState({
                    loginFailed: true
                })
                console.log(e);
            })
        }
        this.setState({
            loading: false
        })
    }

    /**
     * Store user tokens
     * @return void
     */
    storeToken(data) {
        LoginModel.storeTokens(data);
    }

    render() {
        return (
            <div className="d-flex justify-content-center align-items-center login-section">
                <div className="card login-card">
                    <div className="card-body p-4">
                        <h1 className="section-title pt-2 pb-4 text-center">Admin Login</h1>
                        {this.state.loginFailed &&
                            <div class="mb-1 text-center">
                                <span className="input-error-msg">Invalid useranem and password</span>
                            </div>}
                        <div class="mb-3">
                            <input type="text" class="form-control" placeholder="username" ref="username" onChange={this.handleChange.bind(this, "username")}
                                value={this.state.fields["username"]} />
                            <span className="input-error-msg">{this.state.errors["username"]}</span>
                        </div>
                        <div class="mb-3">
                            <input type="password" class="form-control" placeholder="password" ref="username" onChange={this.handleChange.bind(this, "password")}
                                value={this.state.fields["password"]} />
                            <span className="input-error-msg">{this.state.errors["password"]}</span>
                        </div>
                        <button className="btn btn-primary" onClick={() => this.login()}>Login</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;

