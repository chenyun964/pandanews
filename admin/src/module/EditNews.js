import { Component } from "react";
import { Form, Input, Alert, Spin, Select } from 'antd';
import { Link } from "react-router-dom";
import NewsModel from "../model/NewsModel";
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import FileUpload from "../lib/FileUpload";
import CategoryModel from "../model/CategoryModel";

const { TextArea } = Input;
const { Option } = Select;

class EditNews extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: {
                coverImage: null,
                title: null,
                content: null,
                category: null
            },
            loading: false,
            success: false,
            failed: false,
            category: []
        }
    }

    componentDidMount() {
        if (this.props.match.params.id) {
            NewsModel.find(this.props.match.params.id).then(res => {
                console.log(res.data);
                this.setState({
                    data: res.data,
                    imageUrl: res.data.imageUrl
                })
            }).catch(e => {
                console.log(e);
            })
        }
        this.getCategory();
    }

    getCategory() {
        CategoryModel.list().then(res => {
            console.log(res.data);
            this.setState({
                category: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    save() {
        this.setState({
            loading: true,
            success: false,
            failed: false
        })
        
        if (this.state.data.id) {
            NewsModel.update(this.state.data.id, this.state.data).then(res => {
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
            NewsModel.create(this.state.data).then(res => {
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
            <div className="content">
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
                                <h1>Edit News</h1>
                                :
                                <h1>Add News</h1>
                            }
                        </div>
                    </div>
                </header>
                <div className="card p-30" style={{ margin: 28 }}>
                    <div>
                        {this.state.success &&
                            <Alert className="m-b-20" message="Measurement saved." type="success" closable afterClose={() => this.handleClose()} />
                        }
                        {this.state.failed &&
                            <Alert className="m-b-20" message="Failed to save, try again later." type="error" closable afterClose={() => this.handleClose()} />
                        }
                    </div>
                    <Form
                        labelCol={{ span: 4 }}
                        wrapperCol={{ span: 18 }}
                        layout="horizontal"
                    >
                        <Form.Item label="Cover Image">
                            <FileUpload className="image-preview product-image-preview"
                                type="image"
                                src={this.state.data.coverImage}
                                fileOnly
                                onUpload={(d) => this.handleChange(d, "coverImage")}
                            />
                        </Form.Item>
                        <Form.Item label="Title">
                            <Input rows={5} value={this.state.data.title} onChange={(e) => this.handleChange(e.target.value, "title")} />
                        </Form.Item>
                        <Form.Item label="Description">
                            <TextArea  value={this.state.data.description} onChange={(e) => this.handleChange(e.target.value, "description")} />
                        </Form.Item>
                        <Form.Item label="Category">
                            <Select placeholder="Please select category" onChange={(e) => this.handleChange(e, "category")}>
                                {this.state.category.map((cate, k) => {
                                    return <Option key={k} value={cate.id} >{cate.title}</Option>
                                })}
                            </Select>
                        </Form.Item>
                        <Form.Item label="Content">
                            <ReactQuill theme="snow" modules={this.modules} value={this.state.data.content} onChange={(e) => this.handleChange(e, "content")} />
                        </Form.Item>
                        <div className="d-flex justify-content-between">
                            <Link to="/measurement" class="btn btn-secondary" >Cancel</Link>
                            <button type="button" class="btn btn-success" onClick={() => this.save()}>Save</button>
                        </div>
                    </Form>
                </div>
            </div>
        );
    }
}

export default EditNews;