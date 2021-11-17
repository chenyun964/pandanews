import { Component, Fragment } from 'react';
import OrganisationModel from '../model/OrganisationModel';
import UserModel from '../model/UserModel';
import { Modal, Button, Typography, Result, Popconfirm } from 'antd';

const { Text } = Typography;

class Organisation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profile: {},
            fields: {},
            errors: {},
            company: {},
            attendance: {},
            loading: false,
            loginFailed: false
        }
    }

    componentDidMount() {
        UserModel.userOrg().then(res => {
            this.setState({
                company: res.data
            })
        }).catch(e => {
            console.log(e)
        })
    }

    renderOptions() {
        if (this.state.company === "" || this.state.company === undefined || this.state.company == null) {
            return this.renderBothForm();
        } else if (this.state.company.status === 0) {
            return this.renderPending(1);
        } else {
            return this.renderDashboard();
        }
    }

    info() {
        Modal.confirm({
            title: 'This action is non reversible',
            content: (
                <div>
                    <p>Are you sure you wanto to delete <Text mark>{this.state.company.title}</Text></p>
                </div>
            ),
            okText: "Delete",
            okType: "danger",
            onOk: () => {
                this.confirmDelete();
            }
        });
    }

    confirmDelete() {
        OrganisationModel.delete(this.state.company.id).then(res => {
            window.location.reload();
        }).catch(e => {
            console.log(e)
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
            if (!fields["name"].match(/^[a-zA-Z_ ]+$/)) {
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

    save() {
        this.setState({
            loading: true
        })

        if (this.handleValidation()) {
            let data = {
                name: this.state.profile.name,
                contact: this.state.profile.contact,
                email: this.state.profile.email
            }
        }

        this.setState({
            loading: false
        })
    }


    render() {
        return (
            <div className="p-5 flex-fill">
                <div className="flex-fill">
                    <h1>Organisation</h1>
                    <div class="card mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Ogranisation Name: {this.state.company.title}</h5>
                            <p class="card-text">Contact Number: {this.state.company.contact}</p>
                            <Button danger onClick={() => this.info()}>Delete</Button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Organisation;

