import { Component, Fragment } from 'react';
import UserModel from '../model/UserModel';

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profile: {},
            errors: {},
            loading: false
        }
    }

    componentDidMount() {
        UserModel.profile().then(res => {
            this.setState({
                profile: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }


    handleChange(field, e) {
        let profile = this.state.profile;
        profile[field] = e.target.value;
        this.setState({ profile });
    }

    handleValidation() {
        let fields = this.state.profile;
        let errors = {};
        let formIsValid = true;

        //Name
        if (fields["name"] && typeof fields["name"] !== "undefined") {
            if (!fields["name"].match(/^[a-zA-Z]+$/)) {
                formIsValid = false;
                errors["name"] = "Only letters";
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

        //Contact
        if (fields["contacts"] && typeof fields["contact"] !== "undefined") {
            if (!fields["contact"].match(/^[0-9+-]+$/)) {
                formIsValid = false;
                errors["contact"] = "Invalid contact number";
            }
        }

        this.setState({ errors: errors });
        return formIsValid;
    }


    saveProfile() {
        this.setState({
            loading: true
        })

        if (this.handleValidation()) {
            let data = {
                name: this.state.profile.name,
                contact: this.state.profile.contact,
                email: this.state.profile.email
            }
            UserModel.saveProfile(data).then(res => {
                this.setState({
                    profile: res.data
                })
            }).catch(e => {
                console.log(e);
            })
        }

        this.setState({
            loading: false
        })
    }

    render() {
        return (
            <div className="p-5 flex-fill">
                <div className="flex-fill">
                    <h1>Profile</h1>
                </div>
                <div className="d-flex card-container">
                    <div class="card mb-3 flex-fill">
                        {this.state.loading ?
                            <div className="d-flex align-items-center justify-content-center p-5" style={{ "height": "100%" }}>
                                <div class="spinner-border text-primary" role="status">
                                    <span class="sr-only">Loading...</span>
                                </div>
                            </div>
                            :
                            <div class="card-body d-flex flex-column">
                                <div class="mb-3">
                                    <label class="form-label">Your Username</label>
                                    <h5>{this.state.profile.username}</h5>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Display Name</label>
                                    <input type="text" class="form-control" placeholder="display name" ref="display name" onChange={this.handleChange.bind(this, "name")}
                                        value={this.state.profile["name"]} />
                                    <span className="input-error-msg">{this.state.errors["name"]}</span>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Your Contact</label>
                                    <input type="text" class="form-control" placeholder="contact" ref="contact" onChange={this.handleChange.bind(this, "contact")}
                                        value={this.state.profile["contact"]} />
                                    <span className="input-error-msg">{this.state.errors["contact"]}</span>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Your Email</label>
                                    <input type="text" class="form-control" placeholder="email" ref="email" onChange={this.handleChange.bind(this, "email")}
                                        value={this.state.profile["email"]} />
                                    <span className="input-error-msg">{this.state.errors["email"]}</span>
                                </div>
                                <div>
                                    <button class="btn btn-primary" onClick={() => this.saveProfile()}>Save</button>
                                </div>
                            </div>
                        }
                    </div>
                </div>
            </div>
        );
    }

}

export default Profile;