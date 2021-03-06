import React, { Component } from 'react';
import { Table, Input, Popconfirm, Form, Space, Button } from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
import TestSpotModel from '../model/TestSpotModel';
import { TestSpotCreateForm } from '../forms/TestSpotCreateForm';
import { EditableCell } from '../lib/EditableCell';

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

    onCreate(values) {
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
                                <i className="zmdi zmdi-edit zmdi-hc-fw"></i>
                            </button>
                            <Popconfirm title="Sure to delete?" onConfirm={() => this.handleDelete(record.id)}>
                                <button className="btn btn-danger"><i className="la la-trash"></i></button>
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
                <TestSpotCreateForm
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
