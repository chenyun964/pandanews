import { Component, Fragment } from 'react';
import { message, Modal, Table, Tag, Space, Button } from 'antd';
import OrganisationModel from '../model/OrganisationModel';
import UserModel from '../model/UserModel';

const { Column } = Table;

class Employee extends Component {
    constructor(props) {
        super(props);
        this.state = {
            employee: [],
            code: "",
            visible: false
        }
    }

    componentDidMount() {
        this.listEmployee();
        UserModel.userOrg().then(res => {
            this.setState({
                code: res.data.code
            })
        }).catch(e => {
            console.log(e);
        })
    }


    listEmployee() {
        OrganisationModel.employee().then(res => {
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

    promoteEmployee(employee) {
        OrganisationModel.promote(employee.id).then(res => {
            this.listEmployee();
        }).catch(e => {
            console.log(e)
        })
    }

    demoteEmployee(employee) {
        OrganisationModel.demote(employee.id).then(res => {
            this.listEmployee();
        }).catch(e => {
            console.log(e)
        })
    }

    removeAlert(employee) {
        Modal.confirm({
            title: 'This action is non reversible',
            content: (
                <div>
                    <p>Are you sure you want to remove <strong>{employee.name ? employee.name : employee.username}</strong></p>
                </div>
            ),
            okText: "Delete",
            okType: "danger",
            onOk: () => {
                this.removeEmployee(employee);
            }
        });
    }

    removeEmployee(employee) {
        OrganisationModel.remove(employee.id).then(res => {
            this.listEmployee();
        }).catch(e => {
            console.log(e)
        })
    }


    copyCode = () => {
        navigator.clipboard.writeText(window.location.origin + "/employee/invite?code=" + this.state.code)
        message.info('Copied to Clipboard');
    };

    render() {
        return (
            <Fragment>
                <div className="p-5 flex-fill">
                    <div className="flex-fill">
                        <div className="d-flex justify-content-between">
                            <h1>Employee</h1>
                            <Button type="primary" onClick={this.showModel}>Add</Button>
                        </div>
                        <Table dataSource={this.state.employee}>
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
                                render={(id, record) => {
                                    if (id.authorities[0].authority != "ROLE_OWNER") {
                                        return <Space size="middle">
                                            {id.authorities[0].authority == "ROLE_MANAGER" ?
                                                <button className="btn btn-primary" onClick={() => this.demoteEmployee(id)}> Demote </button>
                                                :
                                                <button className="btn btn-primary" onClick={() => this.promoteEmployee(id)}> Promote </button>
                                            }
                                            <button className="btn btn-danger" onClick={() => this.removeAlert(id)}>Remove</button>
                                        </Space>
                                    }

                                }}
                            />
                        </Table>
                    </div>
                </div>
                <Modal title="Add New Employee" visible={this.state.visible} onCancel={this.hideModel} footer={null}>
                    <p className="text-center"><strong >Copy & send the following link to the new Employee</strong></p>
                    <div className="text-center" onClick={this.copyCode}><kbd>{window.location.origin + "/employee/invite?code=" + this.state.code}</kbd></div>
                </Modal>
            </Fragment>

        );
    }
}

export default Employee;

