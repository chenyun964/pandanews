import { Component, Fragment } from "react";
import { Form, Input, Alert, Spin } from 'antd';
import { Link } from "react-router-dom";
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import PolicyModel from "../model/PolicyModel";
import UserModel from "../model/UserModel";

class EditPolicy extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: {
                title: null,
                message: null,
            },
            loading: false,
            success: false,
            failed: false,
            org: {}
        }
    }

    componentDidMount() {
        if (this.props.match.params.id) {
            PolicyModel.get(this.props.match.params.id).then(res => {
                this.setState({
                    data: res.data,
                    imageUrl: res.data.imageUrl
                })
            }).catch(e => {
                console.log(e);
            })
        }
        this.getOrg();
    }

    getOrg(){
        UserModel.userOrg().then(res => {
            this.setState({
                org: res.data
            })
        }).catch(e => {
            console.log(e)
        })
    }

    save() {
        this.setState({
            loading: true,
            success: false,
            failed: false
        })
        if (this.state.data.id) {
            PolicyModel.update(this.state.data.id, this.state.data).then(res => {
                this.setState({
                    success: true,
                    loading: false
                })
            }).catch(e => {
                console.log(e);
                this.setState({
                    loading: false,
                    failed: true
                })
            })
        } else {
            console.log(this.state.org)
            PolicyModel.create(this.state.org.id, this.state.data).then(res => {
                this.setState({
                    success: true,
                    loading: false
                })
            }).catch(e => {
                console.log(e);
                this.setState({
                    loading: false,
                    failed: true
                })
            })
        }

    }

    handleChange(value, field) {
        let data = this.state.data;
        data[field] = value;
        this.setState({ data: data });
    }

    modules = {
        toolbar: [
            [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
            [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
            ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
            ['blockquote', 'code-block'],
            [{ 'align': [] }],
            [{ 'list': 'ordered' }, { 'list': 'bullet' }],
            [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
            ['link'],
            ['clean']                                         // remove formatting button
        ]
    }

    handleClose() {
        this.setState({
            success: false,
            failed: false
        })
    }

    render() {
        return (
            <Fragment>
                <div className="p-5 flex-fill">
                    <div className="flex-fill">
                        {this.state.loading &&
                            <div id="overlay">
                                <div class="w-100 d-flex justify-content-center align-items-center">
                                    <Spin tip="Saving..."></Spin>
                                </div>
                            </div>
                        }
                        <header className="page-header">
                            <div className="d-flex align-items-center">
                                <div className="mr-auto">
                                    {this.props.match.params.id ?
                                        <h1>Edit Policy</h1>
                                        :
                                        <h1>Add Policy</h1>
                                    }
                                </div>
                            </div>
                        </header>
                        <div className="card" style={{ padding: 20 }}>
                            <div>
                                {this.state.success &&
                                    <Alert className="m-b-20" message="Policy saved." type="success" closable afterClose={() => this.handleClose()} />
                                }
                                {this.state.failed &&
                                    <Alert className="m-b-20" message="Failed to save, try again later." type="error" closable afterClose={() => this.handleClose()} />
                                }
                            </div>
                            <Form
                                labelCol={{ span: 4 }}
                                wrapperCol={{ span: 18 }}
                                layout="horizontal"
                                style={{ paddingTop: 30 }}
                            >
                                <Form.Item label="Title">
                                    <Input value={this.state.data.title} onChange={(e) => this.handleChange(e.target.value, "title")} />
                                </Form.Item>
                                <Form.Item label="Message">
                                    <ReactQuill theme="snow" modules={this.modules} value={this.state.data.message} onChange={(e) => this.handleChange(e, "message")} />
                                </Form.Item>
                                <div className="d-flex justify-content-between">
                                    <Link to="/policy" class="btn btn-secondary" >Cancel</Link>
                                    <button type="button" class="btn btn-success" onClick={() => this.save()}>Save</button>
                                </div>
                            </Form>
                        </div>
                    </div>
                </div>
            </Fragment>
        );
    }
}

export default EditPolicy;