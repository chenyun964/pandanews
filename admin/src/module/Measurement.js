import { Component } from 'react';
import MeasurementModel from "../model/MeasurementModel";
import React from 'react';
import { Table, Input, InputNumber, Popconfirm, Form, Typography, Space, Button, Modal, Select } from 'antd';
import '../App.css';

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

const CollectionCreateForm = ({ visible, onCreate, onCancel }) => {
    const [form] = Form.useForm();
    return (
        <Modal
            visible={visible}
            title="Add a new measurement"
            okText="Create"
            cancelText="Cancel"
            onCancel={onCancel}
            onOk={() => {
                form
                    .validateFields()
                    .then((values) => {
                        form.resetFields();
                        onCreate(values);
                    })
                    .catch((info) => {
                        console.log('Validate Failed:', info);
                    });
            }}
        >
            <Form
                form={form}
                labelCol={{ span: 6 }}
                wrapperCol={{ span: 16 }}
                layout="horizontal"
                name="form_in_modal"
                requiredMark={false}
            >
                <Form.Item
                    name="title"
                    label="Industry"
                    rules={[{
                        required: true,
                        message: 'Please input an industry name!',
                    }]}
                >
                    <Input placeholder="Please input an industry name" />
                </Form.Item>
                <Form.Item
                    name="content"
                    label="Measurement"
                    rules={[{
                        required: true,
                        message: 'Please input the measurement details!',
                    }]}
                >
                    <Input placeholder="Please input measurement details" />
                </Form.Item>
                <Form.Item
                    name="imageUrl"
                    label="Icon Link"
                    rules={[{
                        required: true,
                        message: 'Please input the icon link!',
                    }]}
                >
                    <Input placeholder="Please input industry icon link" />
                </Form.Item>
            </Form>
        </Modal>
    );
};

class Measurement extends Component {

    formRef = React.createRef();

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
        MeasurementModel.mList().then((res) => {
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
            title: '',
            content: '',
            date: '',
            ...record,
        });
        this.setState({ editingId: record.id });
    }

    cancel() {
        this.setState({ editingId: -1 });
    }

    handleDelete(id) {
        try {
            MeasurementModel.delete(id);
            this.setState({
                data: this.state.data.filter((item) => item.id !== id),
            });
        } catch (error) {
            console.log(error);
        }
    }

    onAdd() {
        const newData = [{
            id: 0,
            imageUrl: '',
            title: '',
            content: ''
        }, ...this.state.data];
        this.setState({
            data: newData,
            visible: false,
        });
    }

    async save(id) {
        try {
            const row = await this.formRef.current.validateFields();
            const newData = [...this.state.data];
            const index = newData.findIndex((item) => id === item.id);
            if (index > -1) {
                const item = { ...newData[index], ...row, latitude: 0.0, longitude: 0.0 };
                if (id > 0) {
                    newData.splice(index, 1, item);
                    MeasurementModel.update(id, item);
                } else {
                    MeasurementModel.add(item).then(res => {
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
                title: 'Icon',
                dataIndex: 'imageUrl',
                key: 'imageUrl',
                editable: true,
                render: (record) => {
                    return <img src={record} />
                }
            },
            {
                title: 'Industry',
                dataIndex: 'title',
                key: 'title',
                editable: true,
                sorter: (a, b) => a.title.localeCompare(b.title),
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'Measurement Details',
                dataIndex: 'content',
                key: 'content',
                editable: true,
            },
            {
                title: 'Last Updated By',
                dataIndex: 'updatedAt',
                key: 'updatedAt',
                editable: true,
                sorter: (a, b) => a.updatedAt.localeCompare(b.updatedAt),
                sortDirections: ['ascend', 'descend'],

            },
            {
                title: 'operation',
                dataIndex: 'operation',
                render: (_, record) => {
                    if (this.isEditing(record)) {
                        return (
                            <span>
                                <button
                                    className="btn"
                                    onClick={() => this.save(record.id)}
                                    style={{
                                        marginRight: 8,
                                    }}
                                >
                                    Save
                                </button>
                                <Popconfirm title="Sure to cancel?" onConfirm={() => this.cancel()}>
                                    <a>Cancel</a>
                                </Popconfirm>
                            </span>
                        );
                    }
                    return (
                        <Space size='large'>
                            <button className="btn btn-warning" onClick={() => this.edit(record)}> Edit </button>
                            {this.state.data.length >= 1 &&
                                <Popconfirm className="btn btn-danger"  title="Sure to delete?" onConfirm={() => this.handleDelete(record.id)}>
                                    Delete
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
            <div className="content">
                <header className="page-header">
                    <div className="d-flex align-items-center">
                        <div className="mr-auto">
                            <h1>Swab Test Spots</h1>
                        </div>
                        <ul class="actions top-right">
                            <Button
                                type="primary"
                                size="large"
                                onClick={() => {
                                    this.setState({ visible: true });
                                }}
                            >
                                New Test Spot
                            </Button>
                        </ul>
                    </div>
                </header>
                <div class="card" style={{ margin: 28 }}>
                    <Button
                        onClick={() => { this.onAdd(); this.edit(this.state.data[0]) }}
                        type="primary"
                        style={{
                            marginBottom: 16,
                        }}
                    >Add Measurement </Button>

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
            </div>
        );
    }

}

export default Measurement;