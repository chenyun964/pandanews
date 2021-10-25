import { Component } from 'react';
import { Table, Input, Space, Button } from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
import TestSpotModel from '../model/TestSpotModel';

class TestSpotList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            type: props.type,
            data: [],
            errors: {},
            searchText: '',
            searchedColumn: '',
        }
    }

    componentDidMount() {
        TestSpotModel.getByType(this.state.type).then(res => {
            this.setState({ data: res.data });
        }).catch(error => {
            console.log(error);
        })
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

    render() {
        const columns = [
            {
                title: 'Name',
                dataIndex: 'name',
                ...this.getColumnSearchProps('name'),
                sorter: (a, b) => a.name.localeCompare(b.name),
                sortDirections: ['ascend', 'descend'],
                key: 'name',
                render: text => <a>{text}</a>,
            },
            {
                title: 'Address',
                dataIndex: 'address',
                key: 'address',
                responsive: ['lg'],
                ...this.getColumnSearchProps('address'),
            },
            {
                title: 'Operating Hours',
                dataIndex: 'opHours',
                key: 'opHours',
                responsive: ['lg'],
            },
            {
                title: 'Contact',
                dataIndex: 'contact',
                key: 'contact',
                responsive: ['lg'],
            },
        ];
        return (
            <Table columns={columns} dataSource={this.state.data} />
        );
    }
}

export default TestSpotList;
