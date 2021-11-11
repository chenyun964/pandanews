import { Component } from 'react';
import { Link } from 'react-router-dom';
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
            <div className="container">
                <div className="sign-in-form">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="sign-in-heading text-center m-b-20">PandaNews Admin Portal</h5>
                            {this.state.loginFailed &&
                                <div className="mb-1 text-center">
                                    <span className="input-error-msg">Invalid useranem and password</span>
                                </div>
                            }
                            {this.state.expired &&
                                <div className="mb-1 text-center">
                                    <span className="input-error-msg">Session has expired, please login again</span>
                                </div>
                            }
                            <div className="mb-3">
                                <input type="text" className="form-control" placeholder="username" ref="username" onChange={this.handleChange.bind(this, "username")}
                                    value={this.state.fields["username"]} />
                                <span className="input-error-msg">{this.state.errors["username"]}</span>
                            </div>
                            <div className="mb-3">
                                <input type="password" className="form-control" placeholder="password" ref="username" onChange={this.handleChange.bind(this, "password")}
                                    value={this.state.fields["password"]} />
                                <span className="input-error-msg">{this.state.errors["password"]}</span>
                            </div>
                            <button className="btn btn-primary btn-rounded btn-floating btn-lg btn-block" onClick={() => this.login()}>Login</button>
                            <div className="text-center">
                                <p className="text-muted m-t-25 m-b-0 p-0">Don't have an account yet?
                                    <Link to="/signup"> Create an account</Link>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;

