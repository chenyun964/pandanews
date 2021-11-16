import { Component, Fragment } from 'react';
import { Table, Input, Space, Button } from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
import OrganisationModel from '../model/OrganisationModel';
import AttendanceModel from '../model/AttendanceModel';
import moment from "moment";

class Attendance extends Component {
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
        OrganisationModel.employee().then(res =>
            res.data.forEach(employee => {
                AttendanceModel.getAttendanceByUser(employee.id).then(r => {
                    let newData = [...this.state.data, ...r.data]
                    this.setState({ data: newData });
                })
            })
        ).catch(e =>
            console.log(e)
        )
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
        console.log(this.state.data);
        const columns = [
            {
                title: 'Name',
                dataIndex: ['user', 'name'],
                ...this.getColumnSearchProps('name'),
                sorter: (a, b) => a.name.localeCompare(b.name),
                sortDirections: ['ascend', 'descend'],
                key: 'name',
                render: text => <a>{text}</a>,
            },
            {
                title: 'Username',
                dataIndex: ['user', 'username'],
                ...this.getColumnSearchProps('username'),
                sorter: (a, b) => a.username.localeCompare(b.username),
                sortDirections: ['ascend', 'descend'],
                key: 'username',
                render: text => <a>{text}</a>,
            },
            {
                title: 'Punch In Date',
                dataIndex: 'punchInDate',
                key: 'punchInDate',
                responsive: ['lg'],
                ...this.getColumnSearchProps('punchInDate'),
                render: (_, record) => {
                    let date = record.punchInDate[0] + ":" + record.punchInDate[1] + ":" + record.punchInDate[2];
                    return <div>{moment(date, "YYYY-MM-DD").format("YYYY-MM-DD")}</div>
                }
            },
            {
                title: 'Punch In Time',
                dataIndex: 'punchInTime',
                key: 'punchInTime',
                responsive: ['lg'],
                render: (_, record) => {
                    let inTime = record.punchInTime[0] + ":" + record.punchInTime[1];
                    return <div>{moment(inTime, "hh:mm").format("hh:mm")}</div>
                }
            },
            {
                title: 'Punch Out Date',
                dataIndex: 'punchOutDate',
                key: 'punchOutDate',
                responsive: ['lg'],
                ...this.getColumnSearchProps('punchOutDate'),
                render: (_, record) => {
                    let date = record.punchInDate[0] + ":" + record.punchInDate[1] + ":" + record.punchInDate[2];
                    return <div>{moment(date, "YYYY-MM-DD").format("YYYY-MM-DD")}</div>
                }
            },
            {
                title: 'Punch Out Time',
                dataIndex: 'punchOutTime',
                key: 'punchOutTime',
                responsive: ['lg'],
                render: (_, record) => {
                    if (record.punchOutTime) {
                        let outTime = record.punchOutTime[0] + ":" + record.punchOutTime[1];
                        return <div>{moment(outTime, "hh:mm").format("hh:mm")}</div>
                    }
                }
            },
        ];
        return (
            <Fragment>
                <div className="p-5 flex-fill">
                    <div className="flex-fill">
                        <h1>Attendance</h1>
                    </div>
                    <Table columns={columns} dataSource={this.state.data} />
                </div>
            </Fragment>
        );
    }
}

export default Attendance;
