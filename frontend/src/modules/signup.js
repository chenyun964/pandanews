import { Component } from 'react';
import { Link } from "react-router-dom";

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            email: "",
            password: "",
            confirmPassword: ""

        }
    }

    handleField(field, value){
        this.setState({
            [field]: value
        })
    }

    signup(){
        alert(`username: ${this.state.username}\nemail: ${this.state.email} \npassword: ${this.state.password}\nconfirmed password: ${this.state.confirmPassword} `);
    }

    render() {
        return (
            <div className="login-section">
                <div class="d-flex align-items-center">
                    <div class="container login-container">
                        <div class="row">
                            <div className="col-12 col-md-6 login-right text-center">
                                <div className="d-flex flex-column p-5">
                                    <h1 className="section-title pt-2 pb-4">SIGN UP</h1>
                                    <div class="mb-3">
                                        <input type="text" class="form-control" placeholder="Username" value={this.state.username} onChange={(e) => this.handleField("username", e.target.value)}/>
                                    </div>
                                    <div class="mb-3">
                                        <input type="email" class="form-control" placeholder="Email" value={this.state.email} onChange={(e) => this.handleField("email", e.target.value)}/>
                                    </div>
                                    <div class="mb-3">
                                        <input type="password" class="form-control" placeholder="Password" value={this.state.password} onChange={(e) => this.handleField("password", e.target.value)}/>
                                    </div>
                                    <div class="mb-3">
                                        <input type="password" class="form-control" placeholder="Confirm Password" value={this.state.confirmPassword} onChange={(e) => this.handleField("confirmPassword", e.target.value)}/>
                                    </div>
                                    <div className="text-center">
                                        <button className="btn btn-primary" onClick={() => this.signup()}>Sign Up</button>
                                    </div>
                                    <hr />
                                    <p>Already have an account? <Link to="/login">Login Now</Link></p>
                                </div>
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

