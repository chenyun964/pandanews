import { Component, Fragment } from 'react';
import { message, Modal, Table, Tag, Space, Button } from 'antd';
import OrganisationModel from '../model/OrganisationModel';
import UserModel from '../model/UserModel';

const columns = [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        render: text => <a>{text}</a>,
    },
    {
        title: 'Age',
        dataIndex: 'age',
        key: 'age',
    },
    {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',
    },
    {
        title: 'Contact',
        dataIndex: 'contact',
        key: 'contact',
    },
    {
        title: 'Address',
        dataIndex: 'address',
        key: 'address',
    },
    {
        title: 'Action',
        key: 'action',
        render: (text, record) => (
            <Space size="middle">
                <button className="btn btn-primary">Delete</button>
            </Space>
        ),
    },
];

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
        OrganisationModel.employee().then(res => {
            this.setState({
                employee: res.data
            })
        }).catch(e => {
            console.log(e)
        })

        UserModel.userOrg().then(res => {
            this.setState({
                code: res.data.code
            })
        }).catch(e => {
            console.log(e);
        })
    }

    showModal = () => {
        this.setState({ visible: true });
    };

    hideModel = () => {
        this.setState({ visible: false });
    };

    copyCode = () => {
        navigator.clipboard.writeText(window.location.origin + "/employee/invite?code=" +this.state.code)
        message.info('Copyed to Clipboard');
    };

    render() {
        return (
            <Fragment>
                <div className="p-5 flex-fill">
                    <div className="flex-fill">
                        <div className="d-flex justify-content-between">
                            <h1>Employee</h1>
                            <Button type="primary" onClick={this.showModal}>Add</Button>
                        </div>
                        <Table columns={columns} dataSource={this.state.employee} />
                    </div>
                </div>
                <Modal title="Add New Employee" visible={this.state.visible} onCancel={this.hideModel} footer={null}>
                    <p className="text-center"><strong >Copy & send the following link to the new Employee</strong></p>
                    <div className="text-center" onClick={this.copyCode}><kbd>{window.location.origin + "/employee/invite?code=" +this.state.code}</kbd></div>
                </Modal>
            </Fragment>

        );
    }
}

export default Employee;

