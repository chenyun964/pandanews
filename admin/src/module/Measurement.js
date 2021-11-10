import React, { Component } from 'react';
import MeasurementModel from "../model/MeasurementModel";
import { Table, Popconfirm, Space } from 'antd';
import { Link } from 'react-router-dom';
import moment from "moment";

class Measurement extends Component {

    constructor(props) {
        super(props);

        this.state = {
            imageUrl: '',
            title: '',
            content: '',
            data: [],
            editingId: -1,
        }
    }

    componentDidMount() {
        MeasurementModel.list().then((res) => {
            this.setState({ data: res.data });
        }).catch(error => {
            console.log(error);
        })
    }

    handleDelete(id) {
        MeasurementModel.delete(id).then(res => {
            this.setState({
                data: this.state.data.filter((item) => item.id !== id),
            });
        }).catch(error => {
            console.log(error);
        });
    }

    renderColumns() {
        return [
            {
                title: 'Icon',
                dataIndex: 'imageUrl',
                key: 'imageUrl',
                render: (image) => {
                    return <div className="image-thumnail-holder" style={{
                        backgroundImage: `url("${image}")`
                    }}></div>
                }
            },
            {
                title: 'Industry',
                dataIndex: 'title',
                key: 'title',
                sorter: (a, b) => a.title.localeCompare(b.title),
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'Measurement Description',
                dataIndex: 'content',
                key: 'content',
                render: (content) => {
                    return <div dangerouslySetInnerHTML={{ __html: content }}></div>
                }
            },
            {
                title: 'Last Updated By',
                dataIndex: 'updatedAt',
                key: 'updatedAt',
                sorter: (a, b) => moment(a.date).unix() - moment(b.date).unix(),
                sortDirections: ['ascend', 'descend'],

            },
            {
                title: 'Operation',
                dataIndex: 'operation',
                render: (_, record) => (
                    <Space size='large'>
                        <Link className="btn btn-warning" to={"/measurement/" + record.id}>  <i className="zmdi zmdi-edit zmdi-hc-fw"></i> </Link>
                        <Popconfirm className="btn btn-danger" title="Sure to delete?" onConfirm={() => this.handleDelete(record.id)}>
                            <button className="btn btn-danger">
                                <i className="la la-trash"></i>
                            </button>
                        </Popconfirm>
                    </Space>
                )
            }]
    }

    render() {
        return (
            <div className="content">
                <header className="page-header">
                    <div className="d-flex align-items-center">
                        <div className="mr-auto">
                            <h1>Covid Measurements</h1>
                        </div>
                        <ul className="actions top-right">
                            <Link to="/measurement/create" className="ant-btn ant-btn-primary ant-btn-lg color-white">New Measurements</Link>
                        </ul>
                    </div>
                </header>
                <div className="card" style={{ margin: 28 }}>
                    <Table
                        bordered
                        dataSource={this.state.data}
                        columns={this.renderColumns()}
                        pagination={{
                            onChange: () => this.cancel(),
                        }}
                    />
                </div>
            </div>
        );
    }

}

export default Measurement;