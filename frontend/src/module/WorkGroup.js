import { Component, Fragment } from 'react';
import { Table, Tag, Space, Button } from 'antd';
import OrganisationModel from '../model/OrganisationModel';
import UserModel from '../model/UserModel';

const { Column } = Table;

class WorkGroup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            workGroup: [],
            code: "",
            visible: false
        }
    }

    componentDidMount() {
        this.listWorkGroup();

        UserModel.userOrg().then(res => {
            this.setState({
                code: res.data.code
            })
        }).catch(e => {
            console.log(e);
        })
    }


    listWorkGroup() {
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
                            <Column title="Policy ID" dataIndex="id" key="id" />
                            <Column title="Content" dataIndex="message" key="message" />
                            <Column
                                title="Active Policy?"
                                dataIndex="validity"
                                key="validity"
                                render={validity => (
                                    <>
                                        <Tag color={validity ? 'green' : "red"} key={validity}>
                                            {validity ? "Yes" : "No"}
                                        </Tag>
                                    </>
                                )}
                            />
                            <Column
                                title="Action"
                                key="id"
                                render={(id, record) => (
                                    <Space size="middle">
                                        {id.authorities[0].authority == "ROLE_MANAGER" ?
                                            <button className="btn btn-primary" onClick={() => this.deactivatePolicy(id)}> Dectivate </button>
                                            :
                                            <button className="btn btn-primary" onClick={() => this.activatePolicy(id)}> Activate </button>
                                        }
                                        <button className="btn btn-danger" onClick={() => this.removeAlert(id)}>Remove</button>
                                    </Space>
                                )}
                            />
                        </Table>
                    </div>
                </div>
            </Fragment>

        );
    }
}

export default WorkGroup;