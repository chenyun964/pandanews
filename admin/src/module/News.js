import { Component, Fragment } from 'react';
import NewsModel from '../model/NewsModel';
import { Link } from 'react-router-dom';
import { message, Modal, Table, Tag, Space, Button, Popconfirm } from 'antd';

const { Column } = Table;

class News extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: []
        }
    }

    componentDidMount() {
        this.getNews();
    }

    getNews() {
        NewsModel.list().then(res => {
            this.setState({
                news: res.data
            })
        })
    }

    addNewsByAPI() {
        NewsModel.createByAPI().then(res => {
            console.log(res);
            this.setState({
                news: res.data
            })
        })
    }

    handleDelete(id) {
        NewsModel.delete(id).then(res => {
            this.setState({
                data: this.state.data.filter((item) => item.id !== id),
            });
        }).catch(error => {
            console.log(error);
        });
    }

    render() {
        return (
            <div class="content">
                <header class="page-header">
                    <div class="d-flex align-items-center">
                        <div class="mr-auto">
                            <h1>News</h1>
                        </div>
                        <div class="actions top-right">
                            <Space size="large">
                                <button className="ant-btn ant-btn-primary ant-btn-lg color-white" onClick={() => this.addNewsByAPI()}> Add via API</button>
                                <Link to="/news/create" className="ant-btn ant-btn-primary ant-btn-lg color-white"> Add</Link>
                            </Space>
                        </div>
                    </div>
                </header>

                <section class="page-content container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <Table dataSource={this.state.news}>
                                    <Column
                                        title="Pinned"
                                        key="pinned"
                                        render={pinned => {
                                            if (pinned) {
                                                <span class="badge badge-pill badge-warning">Pedning</span>
                                            }
                                        }}
                                    />
                                    <Column
                                        title="Image"
                                        dataIndex="coverImage"
                                        key="coverImage"
                                        render={image => (
                                            <div className="image-thumnail-holder" style={{
                                                backgroundImage: `url("${image}")`
                                            }}></div>
                                        )}
                                    />
                                    <Column title="Title" dataIndex="title" key="title" />
                                    <Column title="Creation Date" dataIndex="createdAt" key="createdAt" />
                                    <Column title="View Count" dataIndex="viewCount" key="viewCount" />
                                    <Column
                                        title="Action"
                                        key="id"
                                        render={(id, record) => (
                                            <Space size="middle">
                                                <Link to={"/news/" + record.id} className="btn btn-warning"><i class="zmdi zmdi-edit zmdi-hc-fw"></i></Link>
                                                <Popconfirm className="btn btn-danger" title="Sure to delete?" onConfirm={() => this.handleDelete(record.id)}>
                                                    <button className="btn btn-danger">
                                                        <i className="la la-trash"></i>
                                                    </button>
                                                </Popconfirm>
                                            </Space>
                                        )}
                                    />
                                </Table>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        );
    }
}

export default News;

