import { Component, Fragment } from 'react';
import { Table, Tag, Space } from 'antd';
import OrganisationModel from '../model/OrganisationModel';
import { Link } from 'react-router-dom';
import UserModel from '../model/UserModel';
import PolicyModel from '../model/PolicyModel';

const { Column } = Table;

class Policy extends Component {
    constructor(props) {
        super(props);
        this.state = {
            policy: [],
            org: {},
            visible: false
        }
    }

    componentDidMount() {
        this.listPolicy();

        UserModel.userOrg().then(res => {
            this.setState({
                org: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    listPolicy() {
        OrganisationModel.policy().then(res => {
            this.setState({
                policy: res.data
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

    deletePolicy(policy) {
        PolicyModel.delete(policy.id).then(res => {
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
                            <Link to="/policy/create" className="ant-btn ant-btn-primary ant-btn-lg color-white">Add Policy</Link>
                        </div>
                        <Table dataSource={this.state.policy}>
                            <Column title="Policy ID" dataIndex="id" key="id" />
                            <Column
                                title="Message"
                                dataIndex="message"
                                key="message"
                                render={message => (
                                    <div dangerouslySetInnerHTML={{ __html: message }}></div>
                                )}
                            />
                            <Column
                                title="Action"
                                key="id"
                                render={(id, record) => (
                                    <Space size="middle">
                                        <button className="btn btn-danger" onClick={() => this.deletePolicy(record)}>Remove</button>
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

export default Policy;