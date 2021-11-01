import { Component } from 'react';
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
            loginFailed: false,
            expired: false
        }
    }

    componentDidMount() {
        if (LoginModel.retrieveToken()) {
            window.location.replace("/dashboard");
        }

        let expired = new URLSearchParams(this.props.location.search).get("session")
        if (expired) {
            this.setState({
                expired: true
            })
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
            expired: false,
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
            <div class="container">
                <div className="sign-in-form">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="sign-in-heading text-center m-b-20">PandaNews Admin Portal</h5>
                            {this.state.loginFailed &&
                                <div class="mb-1 text-center">
                                    <span className="input-error-msg">Invalid useranem and password</span>
                                </div>
                            }
                            {this.state.expired &&
                                <div class="mb-1 text-center">
                                    <span className="input-error-msg">Session has expired, please login again</span>
                                </div>
                            }
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
                            <button class="btn btn-primary btn-rounded btn-floating btn-lg btn-block" onClick={() => this.login()}>Sign In</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;

