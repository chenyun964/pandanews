import React, { Component } from 'react';
import { Table, Modal, Select, Input, InputNumber, Popconfirm, Form, Typography, Space, Button } from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
import VacciSpotModel from '../model/VacciSpotModel';

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

const CollectionCreateForm = ({ visible, onCreate, onCancel }) => {
    const [form] = Form.useForm();
    return (
        <Modal
            visible={visible}
            title="Create a new collection"
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
                    name="name"
                    label="Name"
                    rules={[{
                        required: true,
                        message: 'Please input name of the vaccination spot!',
                    }]}
                >
                    <Input placeholder="Please input name of the vaccination spot" />
                </Form.Item>
                <Form.Item
                    name="address"
                    label="Address"
                    rules={[{
                        required: true,
                        message: 'Please input address!',
                    }]}
                >
                    <Input placeholder="Please input address" />
                </Form.Item>
                <Form.Item
                    name="type"
                    label="Type"
                    hasFeedback
                    rules={[{
                        required: true,
                        message: 'Please select type of building!'
                    }]}
                >
                    <Select placeholder="Please select type of building">
                        <Option value="Vaccination Centre">Vaccination Centre</Option>
                        <Option value="Polyclinic">Polyclinic</Option>
                        <Option value="Clinic">Clinic</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    name="region"
                    label="Region"
                    hasFeedback
                    rules={[{
                        required: true,
                        message: 'Please select region!'
                    }]}
                >
                    <Select placeholder="Please select region">
                        <Option value="Central">Central</Option>
                        <Option value="North">North</Option>
                        <Option value="West">West</Option>
                        <Option value="East">East</Option>
                        <Option value="North East">North East</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    name="vacciType"
                    label="Vaccine Type"
                    hasFeedback
                    rules={[{
                        required: true,
                        message: 'Please select vaccine type!'
                    }]}
                >
                    <Select placeholder="Please select vaccine type">
                        <Option value="Moderna">Moderna</Option>
                        <Option value="Pfizer/Comirnaty">Pfizer/Comirnaty</Option>
                    </Select>
                </Form.Item>
            </Form>
        </Modal>
    );
};

class VacciSpotTable extends Component {
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

    async onCreate(values) {
        const newData = [...this.state.data];
        VacciSpotModel.add(values).then(res => {
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
                width: '22%',
                editable: true,
                sorter: (a, b) => a.name.localeCompare(b.name),
                sortDirections: ['ascend', 'descend'],
                ...this.getColumnSearchProps('name'),
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
                ...this.getColumnSearchProps('address'),
            },
            {
                title: 'Vaccine Type',
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
                    type="primary"
                    onClick={() => {
                        this.setState({ visible: true });
                    }}
                >
                    New Vaccination Spot
                </Button>
                <CollectionCreateForm
                    visible={this.state.visible}
                    onCreate={(values) => this.onCreate(values)}
                    onCancel={() => {
                        this.setState({ visible: false });
                    }}
                />
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

export default VacciSpotTable;
