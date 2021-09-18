import { Component } from 'react';
import { Link } from "react-router-dom";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: ""
        }
    }

    handleField(field, value){
        this.setState({
            [field]: value
        })
    }

    login(){
        alert(`username: ${this.state.username} \npassword: ${this.state.password}`);
    }

    render() {
        return (
            <div className="login-section">
                <div class="d-flex align-items-center">
                    <div class="container login-container">
                        <div class="row">
                            <div className="col-12 col-md-6 login-left">
                                <div className="d-flex align-items-center" style={{ "height": "100%" }}>
                                    <img src="../img/login-img.png" />
                                </div>
                            </div>
                            <div className="col-12 col-md-6 login-right text-center">
                                <div className="d-flex flex-column p-5">
                                    <h1 className="section-title pt-2 pb-4">LOG IN</h1>
                                    <div class="mb-3">
                                        <input type="text" class="form-control" placeholder="username/email" value={this.state.username} onChange={(e) => this.handleField("username", e.target.value)} />
                                    </div>
                                    <div class="mb-3">
                                        <input type="password" class="form-control" placeholder="password" value={this.state.password} onChange={(e) => this.handleField("password", e.target.value)}/>
                                    </div>
                                    <div className="d-flex justify-content-between align-items-center">
                                        <button className="btn btn-primary" onClick={() => this.login()}>Login</button>
                                        <Link to="/forgotpassword">Forgot Password?</Link>
                                    </div>
                                    <hr />
                                    <p>Don't have an account? <Link to="/signup">Create Now</Link></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;

