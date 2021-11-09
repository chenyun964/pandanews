import React, { Component } from "react";
import { Table, Popconfirm, Form, Space, Button } from 'antd';
import { CategoryCreateForm } from '../forms/CategoryCreateForm';
import { EditableCell } from '../lib/EditableCell';
import CategoryModel from "../model/CategoryModel";

class NewsCategory extends Component {
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
        CategoryModel.list().then(res => {
            this.setState({
                data: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    isEditing(record) {
        return record.id == this.state.editingId;
    }

    edit(record) {
        console.log(record)
        this.formRef.current.setFieldsValue({ ...record });
        this.setState({ editingId: record.id });
    }

    cancel() {
        this.setState({ editingId: -1 });
    }

    handleDelete(id) {
        CategoryModel.delete(id).then(res => {
            this.setState({
                data: this.state.data.filter((item) => item.id !== id),
            });
        }).catch(error => {
            console.log(error);
        })
    }

    onCreate(values) {
        let data = values;
        const newData = [...this.state.data];
        CategoryModel.create(data).then(res => {
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
                CategoryModel.update(id, item);
            }
            this.setState({ data: newData, editingId: -1 });
        } catch (error) {
            console.log(error);
        }
    }

    renderColumns() {
        return [
            {
                title: 'Title',
                dataIndex: 'title',
                key: 'title',
                editable: true,
                sorter: (a, b) => a.newCases - b.newCases,
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
                    onCell: (record) => ({
                        record,
                        inputType: col.dataIndex,
                        dataIndex: col.dataIndex,
                        title: col.title,
                        editing: this.isEditing(record)
                    })
                };
        });
    }

    render() {
        return (
            <div className="content">
                <header className="page-header">
                    <div className="d-flex align-items-center">
                        <div className="mr-auto">
                            <h1>News Category</h1>
                        </div>
                        <ul className="actions top-right">
                            <Button
                                type="primary"
                                size="large"
                                onClick={() => {
                                    this.setState({ visible: true });
                                }}
                            >
                                New Category
                            </Button>
                        </ul>
                    </div>
                </header>
                <CategoryCreateForm
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
        )
    }
}

export default NewsCategory