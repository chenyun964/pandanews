import { Table } from 'antd';
import { Component } from 'react';
import VacciSpotModel from '../model/VacciSpotModel';

const columns = [
    {
        title: 'Name',
        dataIndex: 'name',
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
]

class VacciSpotList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            type: props.type,
            data: [],
            errors: {}
        }
    }

    componentDidMount() {
        VacciSpotModel.getByType(this.state.type).then(res => {
            this.setState({ data: res.data });
        }).catch(error => {
            console.log(error);
        })
    }

    render() {
        return (
            <Table columns={columns} dataSource={this.state.data} />
        );
    }
}

export default VacciSpotList;
