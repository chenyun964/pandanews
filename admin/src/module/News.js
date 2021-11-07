import { Component, Fragment } from 'react';
import NewsModel from '../model/NewsModel';
import { message, Modal, Table, Tag, Space, Button } from 'antd';

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

    render() {
        return (
            <div class="content">
                <header class="page-header">
                    <div class="d-flex align-items-center">
                        <div class="mr-auto">
                            <h1>News</h1>
                        </div>
                        <div class="actions top-right">
                            <button className="btn btn-primary" onClick={() => this.addNewsByAPI()}> Add via API</button>
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
                                        render={(id, record) => (
                                            <Space size="middle">
                                                <button className="btn btn-primary" onClick={() => this.demoteEmployee(id)}> Edit </button>
                                                <button className="btn btn-danger" onClick={() => this.removeAlert(id)}>Delete</button>
                                            </Space>
                                        )}
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
                                                <button className="btn btn-primary" onClick={() => this.demoteEmployee(id)}> Edit </button>
                                                <button className="btn btn-danger" onClick={() => this.removeAlert(id)}>Delete</button>
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

