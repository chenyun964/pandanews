import { Component } from 'react';

class Login extends Component {
    render() {
        return (
            <div className="login-section">
                <div class="d-flex align-items-center">
                    <div class="container login-container">
                        <div class="row">
                            <div className="col-12 col-md-6 login-left">
                                <img src="../img/login-img.png" />
                            </div>
                            <div className="col-12 col-md-6 login-right text-center">
                                <div className="d-flex flex-column p-5">
                                    <h1>Login</h1>
                                    <input type="text" placeholder="username/email" />
                                    <input type="password" placeholder="password" />
                                    <div className="d-flex justify-content-between">
                                        <button className="btn btn-primary">Login</button>
                                        <a href="#">Forgot Password?</a>
                                    </div>
                                    <hr />
                                    <div>Don't have an account? <a href="#">Create Now</a></div>
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

