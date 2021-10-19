import { Component, Fragment } from 'react';
import { message, Modal, Table, Tag, Space, Button } from 'antd';
import OrganisationModel from '../model/OrganisationModel';
import UserModel from '../model/UserModel';

const { Column } = Table;

class Policy extends Component {
    constructor(props) {
        super(props);
        this.state = {
            policy: [],
            code: "",
            visible: false
        }
    }

    componentDidMount() {
        this.listPolicy();

        UserModel.userOrg().then(res => {
            this.setState({
                code: res.data.code
            })
        }).catch(e => {
            console.log(e);
        })
    }


    listPolicy() {
        OrganisationModel.policy().then(res => {
            this.setState({
                employee: res.data
            })
        }).catch(e => {
            console.log(e)
        })
    }

    showModel = () => {
        this.setState({ visible: true });
    };

    hideModel = () => {
        this.setState({ visible: false });
    };

    activatePolicy(policy) {
        OrganisationModel.activate(policy.id).then(res => {
            this.listPolicy();
        }).catch(e => {
            console.log(e)
        })
    }

    deactivatePolicy(policy) {
        OrganisationModel.deactivate(policy.id).then(res => {
            this.listPolicy();
        }).catch(e => {
            console.log(e)
        })
    }

    render() {
        return (
            <Fragment>
                <div className="p-5 flex-fill">
                    <div className="flex-fill">
                        <div className="d-flex justify-content-between">
                            <h1>Policy</h1>
                            <Button type="primary" onClick={this.showModel}>Add</Button>
                        </div>
                        <Table dataSource={this.state.policy}>
                            <Column title="Username" dataIndex="username" key="username" />
                            <Column title="Name" dataIndex="name" key="name" />
                            <Column title="Contact" dataIndex="contact" key="contact" />
                            <Column
                                title="Vaccinated"
                                dataIndex="vaccinated"
                                key="vaccinated"
                                render={vaccinated => (
                                    <>
                                        <Tag color={vaccinated ? 'green' : "red"} key={vaccinated}>
                                            {vaccinated ? "Vaccinated" : "Unvaccinated"}
                                        </Tag>
                                    </>
                                )}
                            />
                            <Column
                                title="Role"
                                key="authorities"
                                dataIndex='authorities'
                                render={authorities => (
                                    <>
                                        {authorities.map(auth => {
                                            return (
                                                <Tag color="geekblue" key={auth.authority}>
                                                    {auth.authority.slice(5)}
                                                </Tag>
                                            );
                                        })}
                                    </>
                                )}
                            />
                            <Column
                                title="Action"
                                key="id"
                                render={(id, record) => (
                                    <Space size="middle">
                                        {id.authorities[0].authority == "ROLE_MANAGER" ?
                                            <button className="btn btn-primary" onClick={() => this.demoteEmployee(id)}> Demote </button>
                                            :
                                            <button className="btn btn-primary" onClick={() => this.promoteEmployee(id)}> Promote </button>
                                        }
                                        <button className="btn btn-danger" onClick={() => this.removeAlert(id)}>Remove</button>
                                    </Space>
                                )}
                            />
                        </Table>
                    </div>
                </div>
                <Modal title="Add New Employee" visible={this.state.visible} onCancel={this.hideModel} footer={null}>
                    <p className="text-center"><strong>Copy & send the following link to the new Employee</strong></p>
                    <div className="text-center" onClick={this.copyCode}><kbd>{window.location.origin + "/employee/invite?code=" + this.state.code}</kbd></div>
                </Modal>
            </Fragment>

        );
    }
}

export default Policy;

