import React, { Component } from 'react';
import { Table, Popconfirm, Form, Space, Button } from 'antd';
import StatisticsModel from '../model/StatisticsModel';
import { StatCreateForm } from '../forms/StatCreateForm';
import { EditableCell } from '../lib/EditableCell';
import moment from "moment";

class Statistics extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            editingId: -1,
            visible: false,
        }
    }

    componentDidMount() {
        StatisticsModel.list().then((res) => {
            this.setState({
                data: res.data
            });
        }).catch(error => {
            console.log(error);
        })
    }

    isEditing(record) {
        return record.id == this.state.editingId;
    }

    edit(record) {
        this.formRef.current.setFieldsValue({
            ...record
        });
        this.setState({ editingId: record.id });
    }

    cancel() {
        this.setState({ editingId: -1 });
    }

    handleDelete(id) {
        StatisticsModel.delete(id).then(res => {
            this.setState({
                data: this.state.data.filter((item) => item.id !== id),
            });
        }).catch(error => {
            console.log(error);
        })
    }

    onCreate(values) {
        let data = values;
        data.date = values.date.toDate();
        const newData = [...this.state.data];
        StatisticsModel.create(data).then(res => {
            newData.push(res.data);
            this.setState({
                data: newData,
                visible: false,
            });
        });
    }

    async save(id) {
        try {
            const row = await this.formRef.current.validateFields();
            const newData = [...this.state.data];
            const index = newData.findIndex((item) => id === item.id);
            if (index > -1) {
                const item = { ...newData[index], ...row };
                newData.splice(index, 1, item);
                StatisticsModel.update(id, item);
            }
            this.setState({ data: newData, editingId: -1 });
        } catch (error) {
            console.log(error);
        }
    }

    renderColumns() {
        return [
            {
                title: 'Date',
                dataIndex: 'createdAt',
                key: 'createdAt',
                editable: true,
                sorter: (a, b) => a.createdAt.localeCompare(b.createdAt),
                sortDirections: ['ascend', 'descend'],
                render: (value => moment(value).format("YYYY-MM-DD"))
            },
            {
                title: 'New Cases',
                dataIndex: 'newCases',
                key: 'newCases',
                editable: true,
                sorter: (a, b) => a.newCases - b.newCases,
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'New Deaths',
                dataIndex: 'newDeaths',
                key: 'newDeaths',
                editable: true,
                sorter: (a, b) => a.newDeaths - b.newDeaths,
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'New Recovery',
                dataIndex: 'newRecovery',
                key: 'newRecovery',
                editable: true,
                sorter: (a, b) => a.newRecovery - b.newRecovery,
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'Operation',
                dataIndex: 'operation',
                render: (_, record) => {
                    return this.isEditing(record) ?
                        <Space size='large'>
                            <button className="btn btn-success" onClick={() => this.save(record.id)}>Save</button>
                            <Popconfirm title="Sure to cancel?" onConfirm={() => this.cancel()}>
                                <button className="btn btn-default">Cancel</button>
                            </Popconfirm>
                        </Space>
                        :
                        <Space size='large'>
                            <button className="btn btn-warning" disabled={this.state.editingId != -1} onClick={() => this.edit(record)}>
                                <i className="zmdi zmdi-edit zmdi-hc-fw"></i>
                            </button>
                            <Popconfirm title="Sure to delete?" onConfirm={() => this.handleDelete(record.id)}>
                                <button className="btn btn-danger"><i className="la la-trash"></i></button>
                            </Popconfirm>
                        </Space>
                }
            },
        ].map((col) => {
            return !col.editable ? col :
                {
                    ...col,
                    onCell: (record) => {
                        let type;
                        switch(col.dataIndex){
                            case "createdAt":
                                type = "datepicker";
                                break;
                            default:
                                type = "number";
                        }
                        return ({
                            record,
                            inputType: type,
                            dataIndex: col.dataIndex,
                            title: col.title,
                            editing: this.isEditing(record),
                        })
                    }
                };
        });
    }

    render() {
        return (
            <div className="content">
                <header className="page-header">
                    <div className="d-flex align-items-center">
                        <div className="mr-auto">
                            <h1>Covid 19 Statistics</h1>
                        </div>
                        <ul className="actions top-right">
                            <Button
                                type="primary"
                                size="large"
                                onClick={() => {
                                    this.setState({ visible: true });
                                }}
                            >
                                New Statistics
                            </Button>
                        </ul>
                    </div>
                </header>
                <StatCreateForm
                    visible={this.state.visible}
                    onCreate={(values) => this.onCreate(values)}
                    onCancel={() => {
                        this.setState({ visible: false });
                    }}
                    size="large"
                />
                <div className="card" style={{ margin: 28 }}>
                    <Form ref={this.formRef} name="control-ref">
                        <Table
                            components={{
                                body: {
                                    cell: EditableCell,
                                },
                            }}
                            dataSource={this.state.data}
                            columns={this.renderColumns()}
                            rowClassName="editable-row"
                            pagination={{
                                onChange: () => this.cancel(),
                            }}
                        />
                    </Form>
                </div>
            </div>
        );
    }
}

export default Statistics;
