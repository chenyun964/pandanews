import React, { Component } from 'react';
import { Table, Input, InputNumber, Popconfirm, Form, Typography, Space, Button } from 'antd';
import VacciSpotModel from '../model/VacciSpotModel';

const EditableCell = ({
    editing,
    dataIndex,
    title,
    inputType,
    record,
    index,
    children,
    ...restProps
}) => {
    const inputNode = inputType === 'number' ? <InputNumber /> : <Input />;
    return (
        <td {...restProps}>
            {editing ? (
                <Form.Item
                    name={dataIndex}
                    style={{
                        margin: 0,
                    }}
                    rules={[
                        {
                            required: true,
                            message: `Please Input ${title}!`,
                        },
                    ]}
                >
                    {inputNode}
                </Form.Item>
            ) : (
                children
            )}
        </td>
    );
};

class VacciSpotAdminTable extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            editingId: -1,
        }
    }

    componentDidMount() {
        VacciSpotModel.getAll().then((res) => {
            this.setState({ data: res.data });
        }).catch(error => {
            console.log(error);
        })
    }

    isEditing(record) {
        return record.id == this.state.editingId;
    }

    edit(record) {
        this.formRef.current.setFieldsValue({
            name: '',
            type: '',
            region: '',
            address: '',
            ...record,
        });
        this.setState({ editingId: record.id });
    }

    cancel() {
        this.setState({ editingId: -1 });
    }

    handleDelete(id) {
        try {
            VacciSpotModel.delete(id);
            this.setState({
                data: this.state.data.filter((item) => item.id !== id),
            });
        } catch (error) {
            console.log(error);
        }
    }

    handleAdd() {
        const newData = [{
            id: 0,
            name: '',
            type: '',
            region: '',
            address: '',
            vacciType: '',
        }, ...this.state.data];
        this.setState({
            data: newData,
        });
    }

    async save(id) {
        try {
            const row = await this.formRef.current.validateFields();
            const newData = [...this.state.data];
            const index = newData.findIndex((item) => id === item.id);
            if (index > -1) {
                const item = {...newData[index], ...row, latitude: 0.0, longitude: 0.0};
                if (id > 0) {
                    newData.splice(index, 1, item);
                    VacciSpotModel.update(id, item);
                } else {
                    VacciSpotModel.add(item).then(res => {
                        newData.splice(index, 1, res.data);
                    });
                }
            }
            this.setState({ data: newData, editingId: -1 });
        } catch (error) {
            console.log(error);
        }
    }

    renderColumns() {
        return [
            {
                title: 'Name',
                dataIndex: 'name',
                key: 'name',
                width: '22%',
                editable: true,
                sorter: (a, b) => a.name.localeCompare(b.name),
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'Type',
                dataIndex: 'type',
                key: 'type',
                width: '12%',
                editable: true,
                filters: [
                    {
                        text: 'Vaccination Centre',
                        value: 'Vaccination Centre',
                    },
                    {
                        text: 'Polyclinic',
                        value: 'Polyclinic',
                    },
                    {
                        text: 'Clinic',
                        value: 'Clinic',
                    },
                ],
                onFilter: (value, record) => record.region.indexOf(value) === 0,
            },
            {
                title: 'Region',
                dataIndex: 'region',
                key: 'region',
                width: '10%',
                editable: true,
                filters: [
                    {
                        text: 'Central',
                        value: 'Central',
                    },
                    {
                        text: 'North',
                        value: 'North',
                    },
                    {
                        text: 'North East',
                        value: 'North East',
                    },
                    {
                        text: 'East',
                        value: 'East',
                    },
                    {
                        text: 'West',
                        value: 'West',
                    },
                ],
                onFilter: (value, record) => record.region.indexOf(value) === 0,
            },
            {
                title: 'Address',
                dataIndex: 'address',
                key: 'address',
                width: '35%',
                editable: true,
            },
            {
                title: 'Vaccination Type',
                dataIndex: 'vacciType',
                key: 'vacciType',
                width: '10%',
                editable: true,
                filters: [
                    {
                        text: 'Moderna',
                        value: 'Moderna',
                    },
                    {
                        text: 'Pfizer/Comirnaty',
                        value: 'Pfizer/Comirnaty',
                    },
                ],
                onFilter: (value, record) => record.vacciType.indexOf(value) === 0,
            },
            {
                title: 'operation',
                dataIndex: 'operation',
                render: (_, record) => {
                    if (this.isEditing(record)) {
                        return (
                            <span>
                                <a
                                    href='javascript:;'
                                    onClick={() => this.save(record.id)}
                                    style={{
                                        marginRight: 8,
                                    }}
                                >
                                    Save
                                </a>
                                <Popconfirm title="Sure to cancel?" onConfirm={() => this.cancel()}>
                                    <a>Cancel</a>
                                </Popconfirm>
                            </span>
                        );
                    }
                    return (
                        <Space size='large'>
                            <Typography.Link disabled={this.state.editingId != -1} onClick={() => this.edit(record)}>
                                Edit
                            </Typography.Link>
                            {this.state.data.length >= 1 &&
                                <Popconfirm title="Sure to delete?" onConfirm={() => this.handleDelete(record.id)}>
                                    <a>Delete</a>
                                </Popconfirm>
                            }
                        </Space>
                    );
                }
            },
        ].map((col) => {
            if (!col.editable) {
                return col;
            }
            return {
                ...col,
                onCell: (record) => ({
                    record,
                    inputType: 'text',
                    dataIndex: col.dataIndex,
                    title: col.title,
                    editing: this.isEditing(record),
                }),
            };
        });
    }

    render() {
        return (
            <div>
                <Button
                    onClick={() => this.handleAdd()}
                    type="primary"
                    style={{
                        marginBottom: 16,
                    }}
                >
                    Add a new spot
                </Button>
                <Form ref={this.formRef} name="control-ref">
                    <Table
                        components={{
                            body: {
                                cell: EditableCell,
                            },
                        }}
                        bordered
                        dataSource={this.state.data}
                        columns={this.renderColumns()}
                        rowClassName="editable-row"
                        pagination={{
                            onChange: () => this.cancel(),
                        }}
                    />
                </Form>
            </div>
        );
    }
}

export default VacciSpotAdminTable;
