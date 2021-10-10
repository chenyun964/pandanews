import { Component } from 'react';
import LoginModel from '../model/LoginModel';
import OrganisationModel from '../model/OrganisationModel';
import UserModel from '../model/UserModel';

class Invite extends Component {
    constructor(props){
        super(props);
        this.state = {
            organisation: {},
            user: {}
        }
    }

    componentDidMount(){
        let code = new URLSearchParams(this.props.location.search).get("code")

        if(!LoginModel.retrieveToken()){
            let redirect = encodeURIComponent(window.location.pathname + "?code=" + code);
            window.location.replace("/login?redirect=" + redirect)
        }
        OrganisationModel.getByCode(code).then(res => {
            this.setState({
                organisation: res.data
            })
        }).catch(e => {
            console.log(e)
        })

        UserModel.profile().then(res => {
            this.setState({
                user: res.data
            })
        }).catch(e => {
            console.log(e)
        })
    }

    accept(){
        OrganisationModel.join(this.state.organisation).then(res => {
            window.location.replace("/dashboard")
        }).catch(e => {
            console.log(e);
        })
    }

    reject(){
        window.location.replace("/");
    }

    render() {
        return (
            <div className="login-section">
                <div class="d-flex align-items-center">
                    <div class="container login-container">
                        <div class="row">
                            <div className="col-12">
                                <div className="d-flex align-items-center flex-column p-4" style={{ "height": "100%" }}>
                                    <h2>Join us now</h2>
                                    <div className="mb-3">Hey <strong>{this.state.user.username}</strong>, we inviting you to join <strong>{this.state.organisation.title}</strong></div>
                                    <button className="btn btn-success mb-3" onClick={() => this.accept()}>Accept</button>
                                    <button className="btn btn-light" onClick={() => this.reject()}>Reject</button>
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

export default Invite;