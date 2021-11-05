import React, { Component } from 'react';
import { Table, Modal, Select, Input, InputNumber, Popconfirm, Form, Typography, Space, Button } from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
import TestSpotModel from '../model/TestSpotModel';

const { Option } = Select;

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

const SpotCreateForm = ({ visible, onCreate, onCancel }) => {
    const [form] = Form.useForm();
    return (
        <Modal
            visible={visible}
            title="Create a new swab test spot"
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
                labelCol={{ span: 7 }}
                wrapperCol={{ span: 18}}
                layout="horizontal"
                name="form_in_modal"
                requiredMark={false}
            >
                <Form.Item
                    name="name"
                    label="Name"
                    rules={[{
                        required: true,
                        message: 'Please input name of the swab test spot!',
                    }]}
                >
                    <Input placeholder="Please input name of the swab test spot" />
                </Form.Item>
                <Form.Item
                    name="address"
                    label="Address"
                    rules={[{
                        required: true,
                        message: 'Please input address!',
                    }]}
                >
                    <Input.TextArea placeholder="Please input address" />
                </Form.Item>
                <Form.Item
                    name="type"
                    label="Type"
                    hasFeedback
                    rules={[{
                        required: true,
                        message: 'Please select type of test!'
                    }]}
                >
                    <Select placeholder="Please select type of test">
                        <Option value="PCR">PCR</Option>
                        <Option value="ART">ART</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    name="opHours"
                    label="Operating Hours"
                    rules={[{
                        required: true,
                        message: 'Please input operating hours!',
                    }]}
                >
                    <Input.TextArea placeholder="Please input operating hours" />
                </Form.Item>
                <Form.Item
                    name="contact"
                    label="Contact"
                    rules={[{
                        required: true,
                        message: 'Please input contact!',
                    }]}
                >
                    <Input placeholder="Please input contact" />
                </Form.Item>
            </Form>
        </Modal>
    );
};

class TestSpotTable extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            editingId: -1,
            searchText: '',
            searchedColumn: '',
            visible: false,
        }
    }

    componentDidMount() {
        TestSpotModel.getAll().then((res) => {
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
            address: '',
            opHours: '',
            contact: '',
            ...record,
        });
        this.setState({ editingId: record.id });
    }

    cancel() {
        this.setState({ editingId: -1 });
    }

    handleDelete(id) {
        try {
            TestSpotModel.delete(id);
            this.setState({
                data: this.state.data.filter((item) => item.id !== id),
            });
        } catch (error) {
            console.log(error);
        }
    }

    async onCreate(values) {
        const newData = [...this.state.data];
        TestSpotModel.add(values).then(res => {
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
                const item = { ...newData[index], ...row, latitude: 0.0, longitude: 0.0 };
                if (id > 0) {
                    TestSpotModel.update(id, item).then((res) => {
                        newData.splice(index, 1, res.data);
                        this.setState({ data: newData, editingId: -1 });
                    });
                }
            }
        } catch (error) {
            console.log(error);
        }
    }

    getColumnSearchProps(dataIndex) {
        return ({
            filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
                <div style={{ padding: 8 }}>
                    <Input
                        ref={node => {
                            this.searchInput = node;
                        }}
                        placeholder={`Search ${dataIndex}`}
                        value={selectedKeys[0]}
                        onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                        onPressEnter={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
                        style={{ marginBottom: 8, display: 'block' }}
                    />
                    <Space>
                        <Button
                            type="primary"
                            onClick={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
                            icon={<SearchOutlined />}
                            size="small"
                            style={{ width: 90 }}
                        >
                            Search
                        </Button>
                        <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
                            Reset
                        </Button>
                    </Space>
                </div>
            ),
            filterIcon: filtered => <SearchOutlined style={{ color: filtered ? '#1890ff' : undefined }} />,
            onFilter: (value, record) =>
                record[dataIndex]
                    ? record[dataIndex].toString().toLowerCase().includes(value.toLowerCase())
                    : '',
            onFilterDropdownVisibleChange: visible => {
                if (visible) {
                    setTimeout(() => this.searchInput.select(), 100);
                }
            },
            render: text =>
                this.state.searchedColumn === dataIndex ? (
                    <Highlighter
                        highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
                        searchWords={[this.state.searchText]}
                        autoEscape
                        textToHighlight={text ? text.toString() : ''}
                    />
                ) : (
                    text
                ),
        })
    }

    handleSearch(selectedKeys, confirm, dataIndex) {
        confirm();
        this.setState({
            searchText: selectedKeys[0],
            searchedColumn: dataIndex,
        });
    }

    handleReset(clearFilters) {
        clearFilters();
        this.setState({ searchText: '' });
    }

    renderColumns() {
        return [
            {
                title: 'Name',
                dataIndex: 'name',
                key: 'name',
                width: '20%',
                editable: true,
                sorter: (a, b) => a.name.localeCompare(b.name),
                sortDirections: ['ascend', 'descend'],
                ...this.getColumnSearchProps('name'),
            },
            {
                title: 'Type',
                dataIndex: 'type',
                key: 'type',
                width: '3%',
                editable: true,
                filters: [
                    {
                        text: 'PCR',
                        value: 'PCR',
                    },
                    {
                        text: 'ART',
                        value: 'ART',
                    }
                ],
                onFilter: (value, record) => record.region.indexOf(value) === 0,
            },
            {
                title: 'Address',
                dataIndex: 'address',
                key: 'address',
                width: '35%',
                editable: true,
                ...this.getColumnSearchProps('address'),
            },
            {
                title: 'Operating Hours',
                dataIndex: 'opHours',
                key: 'opHours',
                width: '35%',
                editable: true,
            },
            {
                title: 'Contact',
                dataIndex: 'contact',
                key: 'contact',
                width: '8%',
                editable: true,
            },
            {
                title: 'Last Updated By',
                dataIndex: ["admin", "username"],
                key: ["admin", "username"],
                width: '8%',
                editable: false,
                render: (username) => username,
                ...this.getColumnSearchProps('username'),
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
                <SpotCreateForm
                    visible={this.state.visible}
                    onCreate={(values) => this.onCreate(values)}
                    onCancel={() => {
                        this.setState({ visible: false });
                    }}
                    size="large"
                />
                <div class="card" style={{ margin: 28 }}>
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

export default TestSpotTable;
