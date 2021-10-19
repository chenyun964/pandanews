import { Component } from 'react';
import { Table, Input, Space, Button } from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
import VacciSpotModel from '../model/VacciSpotModel';

class VacciSpotList extends Component {
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
        VacciSpotModel.getByType(this.state.type).then(res => {
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
                ...this.getColumnSearchProps('address'),
                sorter: (a, b) => a.name.localeCompare(b.name),
                sortDirections: ['ascend', 'descend'],
                key: 'name',
                render: text => <a>{text}</a>,
            },
            {
                title: 'Region',
                dataIndex: 'region',
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
                key: 'region',
                responsive: ['lg'],
            },
            {
                title: 'Address',
                dataIndex: 'address',
                key: 'address',
                responsive: ['lg'],
                ...this.getColumnSearchProps('address'),
            },
            {
                title: 'Vaccine Type',
                dataIndex: 'vacciType',
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
                key: 'vacciType',
                responsive: ['lg'],
            }
        ];
        return (
            <Table columns={columns} dataSource={this.state.data} />
        );
    }
}

export default VacciSpotList;
