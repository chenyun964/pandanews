import React, { Component } from 'react';
import { Table, Modal, Select, Input, InputNumber, Popconfirm, Form, Typography, Space, Button, DatePicker } from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
import StatisticsModel from '../model/StatisticsModel';

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

const StatCreateForm = ({ visible, onCreate, onCancel }) => {
    const [form] = Form.useForm();
    return (
        <Modal
            visible={visible}
            title="New Covid Statistic"
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
                wrapperCol={{ span: 18 }}
                layout="horizontal"
                name="form_in_modal"
                requiredMark={false}
            >
                <Form.Item
                    name="date"
                    label="Date"
                    rules={[{
                        required: true,
                        message: 'Please select the date of statistics!',
                    }]}
                >
                    <DatePicker style={{ width: '100%' }} />
                </Form.Item>
                <Form.Item
                    name="newCases"
                    label="New Cases"
                    rules={[{
                        required: true,
                        message: 'Please input news covid cases!',
                    }]}
                >
                    <InputNumber min={0} defaultValue={0} style={{ width: '100%' }} />
                </Form.Item>
                <Form.Item
                    name="newDeaths"
                    label="New Deaths"
                    rules={[{
                        required: true,
                        message: 'Please input news covid cases!',
                    }]}
                >
                    <InputNumber min={0} defaultValue={0} style={{ width: '100%' }} />
                </Form.Item>
                <Form.Item
                    name="newRecovery"
                    label="New Recovery"
                    rules={[{
                        required: true,
                        message: 'Please input news covid cases!',
                    }]}
                >
                    <InputNumber min={0} defaultValue={0} style={{ width: '100%' }} />
                </Form.Item>
            </Form>
        </Modal>
    );
};

class Statistics extends Component {
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
        StatisticsModel.list().then((res) => {
            console.log(res.data);
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
            StatisticsModel.delete(id);
            this.setState({
                data: this.state.data.filter((item) => item.id !== id),
            });
        } catch (error) {
            console.log(error);
        }
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
                title: 'Date',
                dataIndex: 'createdAt',
                key: 'createdAt',
                editable: true,
                sorter: (a, b) => a.name.localeCompare(b.name),
                sortDirections: ['ascend', 'descend'],
                ...this.getColumnSearchProps('createdAt'),
            },
            {
                title: 'newCases',
                dataIndex: 'newCases',
                key: 'newCases',
                editable: true,
            },
            {
                title: 'newDeaths',
                dataIndex: 'newDeaths',
                key: 'newDeaths',
                editable: true,
            },
            {
                title: 'newRecovery',
                dataIndex: 'newRecovery',
                key: 'newRecovery',
                editable: true,
            },
            {
                title: 'Operation',
                dataIndex: 'operation',
                render: (_, record) => {
                    if (this.isEditing(record)) {
                        return (
                            <Space size='large'>
                                <button className="btn btn-success" onClick={() => this.save(record.id)}>Save</button>
                                <Popconfirm title="Sure to cancel?" onConfirm={() => this.cancel()}>
                                    <button className="btn btn-default">Cancel</button>
                                </Popconfirm>
                            </Space>
                        );
                    }
                    return (
                        <Space size='large'>
                            <button className="btn btn-warning" disabled={this.state.editingId != -1} onClick={() => this.edit(record)}>
                                <i class="zmdi zmdi-edit zmdi-hc-fw"></i>
                            </button>
                            <Popconfirm title="Sure to delete?" onConfirm={() => this.handleDelete(record.id)}>
                                <button className="btn btn-danger"><i class="la la-trash"></i></button>
                            </Popconfirm>
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
                            <h1>Covid 19 Statistics</h1>
                        </div>
                        <ul class="actions top-right">
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

export default Statistics;
