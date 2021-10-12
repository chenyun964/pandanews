import { Component, Fragment } from 'react';
import { Link } from "react-router-dom";
import OrganisationModel from "../model/OrganisationModel";
import { Modal, Button, Typography } from 'antd';

const { Text } = Typography;

class Organisation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            organisations: [],
            deleteModal: false
        }
    }

    componentDidMount() {
        this.listOrg();
    }

    listOrg(){
        OrganisationModel.list().then(res => {
            this.setState({
                organisations: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    approveOrg(id, i) {
        OrganisationModel.approve(id).then(res => {
            let org = this.state.organisations;
            org[i].status = 1;
            this.setState({
                organisations: org
            })
        }).catch(e => {
            console.log(e)
        })
    }

    info(id, i) {
        Modal.confirm({
            title: 'This action is non reversible',
            content: (
                <div>
                    <p>Are you sure you wanto to delete <Text mark>{this.state.organisations[i].title}</Text></p>
                </div>
            ),
            okText: "Delete",
            okType: "danger",
            onOk: () => {
                this.confirmDelete(id, i);
            }
        });
    }

    confirmDelete(id, i) {
        OrganisationModel.delete(id).then(res => {
            this.listOrg();
        }).catch(e => {
            console.log(e)
        })
    }

    renderOrg() {
        return this.state.organisations.map((org, i) => {
            return <tr key={i}>
                <td>
                    <img class="align-self-center mr-3 ml-2 w-50 rounded-circle" src="../assets/img/avatars/27.jpg" alt="" />
                    <strong class="nowrap">{org.title}</strong>
                </td>
                <td>{org.contact}</td>
                <td>{this.renderStauts(org.status)}</td>
                <td>
                    {org.status == 0 &&
                        <button className="btn btn-primary" onClick={() => this.approveOrg(org.id, i)}>Approve</button>
                    }
                    {org.status ? 
                        <Fragment>
                            <Button danger onClick={() => this.info(org.id, i)}>Delete</Button>
                        </Fragment>
                        :
                        ""
                    }
                </td>
            </tr>
        })
    }

    renderStauts(status) {
        switch (status) {
            case 0:
                return <span class="badge badge-pill badge-warning">Pedning</span>
            case 1:
                return <span class="badge badge-pill badge-success">Active</span>
            default:
                return
        }
    }


    render() {
        return (
            <div className="content">
                <header className="page-header">
                    <div className="d-flex align-items-center">
                        <div className="mr-auto">
                            <h1>Organisation</h1>
                        </div>
                        <ul class="actions top-right">
                            <li class="dropdown">
                                <a href="javascript:void(0)" class="btn btn-fab" data-toggle="dropdown" aria-expanded="false">
                                    <i class="la la-ellipsis-h"></i>
                                </a>
                                <div class="dropdown-menu dropdown-icon-menu dropdown-menu-right">
                                    <div class="dropdown-header">
                                        Quick Actions
                                    </div>
                                    <a href="#" class="dropdown-item">
                                        <i class="icon dripicons-clockwise"></i> Refresh
                                    </a>
                                    <a href="#" class="dropdown-item">
                                        <i class="icon dripicons-gear"></i> Manage Widgets
                                    </a>
                                    <a href="#" class="dropdown-item">
                                        <i class="icon dripicons-cloud-download"></i> Export
                                    </a>
                                    <a href="#" class="dropdown-item">
                                        <i class="icon dripicons-help"></i> Support
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </header>

                <section class="page-content container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-header">
                                    <span class="d-inline-block">Ogranisation Report</span>
                                </div>
                                <div class="card-body p-0">
                                    <table class="table v-align-middle">
                                        <thead class="bg-light">
                                            <tr>
                                                <th class="p-l-20">Title</th>
                                                <th>Contact</th>
                                                <th>Status</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {this.renderOrg()}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        );
    }
}

export default Organisation;

