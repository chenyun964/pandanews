import { Component } from 'react';
import { Table } from 'antd';
import { Result } from 'antd';
import StatisticsModel from '../model/StatisticsModel';
import { Line, Bar } from 'react-chartjs-2';
import moment from 'moment';

class Covid19 extends Component {
    constructor(props) {
        super(props);
        this.state = {
            summary: {},
            data: [],
            dailyGraph: null,
            totalGraph: null
        }
    }

    componentDidMount() {
        this.getSummary();
        this.listData();
    }

    getSummary() {
        StatisticsModel.summary().then(res => {
            this.setState({
                summary: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    listData() {
        StatisticsModel.list().then(res => {
            this.setState({
                data: res.data
            })
            this.dataProcess(res.data);
        }).catch(e => {
            console.log(e)
        })
    }

    dataProcess(datas) {
        let data = datas.reverse();
        let dates = []

        let newCase = [];
        let recoveryCase = [];
        let deathCase = [];

        let totalNew = [];
        let totalRecovery = [];
        let totalDeath = [];

        data.map((d => {
            dates.push(moment(d.date).format("YYYY-MM-DD"));
            newCase.push(d.newCases);
            recoveryCase.push(d.newRecovery);
            deathCase.push(d.newDeaths);
            if (totalNew.length == 0) {
                totalNew.push(d.newCases);
            } else {
                totalNew.push(d.newCases + totalNew[totalNew.length - 1]);
            }

            if (totalRecovery.length == 0) {
                totalRecovery.push(d.newCases);
            } else {
                totalRecovery.push(d.newRecovery + totalRecovery[totalRecovery.length - 1]);
            }

            if (totalDeath.length == 0) {
                totalDeath.push(d.newCases);
            } else {
                totalDeath.push(d.newDeaths + totalDeath[totalDeath.length - 1]);
            }
        }))

        let daily = {
            labels: dates,
            datasets: [
                {
                    label: 'New Cases',
                    fill: false,
                    lineTension: 0,
                    backgroundColor: '#ff6969',
                    borderColor: '#ff6969',
                    borderWidth: 2,
                    data: newCase
                },
                {
                    label: 'Recovery Cases',
                    fill: false,
                    lineTension: 0,
                    backgroundColor: '#198754',
                    borderColor: '#198754',
                    borderWidth: 2,
                    data: recoveryCase
                },
                {
                    label: 'Death Cases',
                    fill: false,
                    lineTension: 0,
                    backgroundColor: '#898989',
                    borderColor: '#898989',
                    borderWidth: 2,
                    data: deathCase
                },
            ]
        }

        let total = {
            labels: dates,
            datasets: [
                {
                    label: 'New Cases',
                    fill: false,
                    lineTension: 0,
                    backgroundColor: '#ff6969',
                    borderColor: '#ff6969',
                    borderWidth: 2,
                    data: totalNew
                },
                {
                    label: 'Recovery Cases',
                    fill: false,
                    lineTension: 0,
                    backgroundColor: '#198754',
                    borderColor: '#198754',
                    borderWidth: 2,
                    data: totalRecovery
                },
                {
                    label: 'Death Cases',
                    fill: false,
                    lineTension: 0,
                    backgroundColor: '#898989',
                    borderColor: '#898989',
                    borderWidth: 2,
                    data: totalDeath
                },
            ]
        }

        this.setState({
            dailyGraph: daily,
            totalGraph: total
        })
    }

    renderColumns() {
        return [
            {
                title: 'Date',
                dataIndex: 'createdAt',
                key: 'createdAt',
                editable: true,
                sorter: (a, b) => a.createdAt.localeCompare(b.createdAt),
                sortDirections: ['ascend', 'descend'],
                render: (value => moment(value).format("YYYY-MM-DD"))
            },
            {
                title: 'New Cases',
                dataIndex: 'newCases',
                key: 'newCases',
                editable: true,
                sorter: (a, b) => a.newCases - b.newCases,
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'New Deaths',
                dataIndex: 'newDeaths',
                key: 'newDeaths',
                editable: true,
                sorter: (a, b) => a.newDeaths - b.newDeaths,
                sortDirections: ['ascend', 'descend'],
            },
            {
                title: 'New Recovery',
                dataIndex: 'newRecovery',
                key: 'newRecovery',
                editable: true,
                sorter: (a, b) => a.newRecovery - b.newRecovery,
                sortDirections: ['ascend', 'descend'],
            }
        ]
    }

    render() {
        return (
            <div style={{ padding: 25 }}>
                <div className="row" >
                    <div className="col-md-12 col-lg-6 col-xl-4">
                        <div className="covid-case covid-case-danger">
                            <div className="label">
                                Total Cases
                            </div>
                            <h3 className="result">{this.state.summary.totalCases}</h3>
                        </div>
                    </div>
                    <div className="col-md-12 col-lg-6 col-xl-4">
                        <div className="covid-case covid-case-success">
                            <div className="label">
                                Total Recovery
                            </div>
                            <h3 className="result">{this.state.summary.totalRecovery}</h3>
                        </div>
                    </div>
                    <div className="col-md-12 col-lg-6 col-xl-4">
                        <div className="covid-case covid-case-default">
                            <div className="label">
                                Total Death
                            </div>
                            <h3 className="result">{this.state.summary.totalDeath}</h3>
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="col-lg-6 col-12">
                        <div className="card">
                            <Table
                                dataSource={this.state.data}
                                columns={this.renderColumns()}
                                pagination={{
                                    onChange: () => this.cancel(),
                                }}
                            />
                        </div>
                    </div>
                    <div className="col-lg-6 col-12">
                        <iframe src="https://d209m3w127yzkd.cloudfront.net/index.html" className="covid-case-map" />
                    </div>
                </div>
                <div className="row">
                    <div className="col-lg-6 col-12">
                        <div className="card">
                            <div className="card-header">
                                <h5>Daily Cases</h5>
                            </div>
                            {this.state.dailyGraph != null ?
                                <Bar style={{ margin: 14 }} data={this.state.dailyGraph} />
                                :
                                <Result
                                    status="404"
                                    title="404"
                                    subTitle="No data found."
                                />
                            }
                        </div>
                    </div>
                    <div className="col-lg-6 col-12">
                        <div className="card">
                            <div className="card-header">
                                <h5>Total Cases</h5>
                            </div>
                            {this.state.totalGraph != null ?
                                <Line style={{ margin: 14 }} data={this.state.totalGraph} />
                                :
                                <Result
                                    status="404"
                                    title="404"
                                    subTitle="No data found."
                                />
                            }
                        </div>
                    </div>
                </div>

            </div >
        )
    }
}

export default Covid19;
