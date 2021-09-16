import { Component } from 'react';

class Login extends Component {
    render() {
        return (
            <div className="login-section">
                <div class="d-flex align-items-center">
                    <div class="container login-container">
                        <div class="row">
                            <div className="col-12 col-md-6 login-left">
                                <img src="../img/login-img.png"/>
                            </div>
                            <div className="col-12 col-md-6 login-right text-center">
                                <div>Login</div>
                                <input placeholder="username/email" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        );
    }

}

export default Login;

