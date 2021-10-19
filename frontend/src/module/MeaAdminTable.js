import { Component } from 'react';
import MeasurementModel from "../model/MeasurementModel";
import React from 'react';
import { Table, Input, InputNumber, Popconfirm, Form, Typography, Space, Button } from 'antd';
//import Popup from "reactjs-popup";
//import "reactjs-popup/dist/index.css";
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

class MeaAdminTable extends Component {

    formRef = React.createRef();

    constructor(props) {
        super(props);


        // this.onChangeIcon = this.onChangeIcon.bind(this);
        // this.onChangeIndustry = this.onChangeIndustry.bind(this);
        // this.onChangeContent = this.onChangeContent.bind(this);
        // this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            imageUrl: '',
            title: '',
            content: '',
            data: [],
            editingId: -1,
        }


        // this.state = {
        //     data: [],
        //     editingId: -1,
        // }
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

    // onChangeIcon(e) {
    //     this.setState({ imageUrl: e.target.value })
    // }

    // onChangeIndustry(e) {
    //     this.setState({ title: e.target.value })
    // }

    // onChangeContent(e) {
    //     this.setState({ content: e.target.value })
    // }

    // onSubmit(e) {
    //     const newData = [{
    //             id:0,
    //             imageUrl: '',
    //             title: '',
    //             content: ''
    //     }, ...this.state.data];
    //         this.setState({
    //             data: newData,
    //         });
    // }

    onEdit(){
        this.save(this.state.data[0]);
        this.edit(this.state.data[0]);
    }


    onAdd() {
        const newData = [{
                id:0,
                imageUrl: '',
                title: '',
                content: ''
        }, ...this.state.data];
            this.setState({
                data: newData,
            });
        this.onEdit();
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
                title: 'Icon Link',
                dataIndex: 'imageUrl',
                key: 'imageUrl',
                width: '18%',
                editable: true,
            },
            {
                title: 'Industry',
                dataIndex: 'title',
                key: 'title',
                width: '18%',
                editable: true,
                sorter: (a, b) => a.title.localeCompare(b.title),
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'Measurement Details',
                dataIndex: 'content',
                key: 'content',
                width: '45%',
                editable: true,

            },
            {
                title: 'Last Updated By',
                dataIndex: 'updatedAt',
                key: 'updatedAt',
                width: '20%',
                editable: true,

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
                <Form ref={this.formRef} name="control-ref">
                    {/* <Popup trigger={<button>Add</button>}>
                        <div> 
                        <form id="form">
                                <button type="button" id="close" onclick="hide()">X</button>
                                <p1>Icon Link:</p1>
                                <input id="imageUrl" name="imageUrl" type="text" value={this.state.imageUrl} onChange={this.onChangeIcon} />
                                <p1>Industry:</p1>
                                <input id="title" name="title" type="text" value={this.state.title} onChange={this.onChangeIndustry}/>
                                <p1>Measurement Details:</p1>
                                <input id="content" name="content" type="text" value={this.state.content} onChange={this.onChangeContent}/>
                                <button onClick={() => {this.onSubmit()}}>Save</button>
                            </form>

                        </div>
                    </Popup> */}
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
                    <Button
                        id="add"
                        onClick= {() => {this.onAdd()}}
                        //onClick={() => {this.onAdd(); this.edit(this.state.data[0])}}
                        type="primary"
                        style={{
                            marginBottom: 16,
                        }}
                    >
                        Add a new measurement
                    </Button> 
                   

                </Form>



            </div>
        );
    }

}

export default MeaAdminTable;