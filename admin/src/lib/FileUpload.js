import React, { Component, Fragment } from 'react';
import axios from 'axios';
const API_HOST = process.env.REACT_APP_API_URL;

class FileUpload extends Component {

    constructor(props) {
        super(props);
        this.inputRef = React.createRef();
    }

    handleClick(e) {
        this.inputRef.current.click();
    }

    handleUpload(e) {

        let files = e.target.files || e.dataTransfer.files;
        if (!files.length)
            return;
        // check file type
        let file = files[0];
        let mime = file.type.toLowerCase()

        if (this.props.type == 'image') {
            // check that file is an image
            if (['image/jpeg', 'image/jpg', 'image/png', 'image/gif'].indexOf(mime) == -1) {
                alert('Please choose an image.');
                return;
            }
        }

        const url = API_HOST + "/image";
        const formData = new FormData();
        formData.append('file', file)
        if (typeof this.props.folder !== 'undefined') {
            formData.append('folder', this.props.folder);
        }
        const cfg = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }

        axios.post(url, formData, cfg).then(res => {
            let data = res.data.fileDownloadUri;
            if (this.props.onUpload) {
                this.props.onUpload(data);
            }
        }).catch(error => console.log(error))
    }

    displayImage(src) {
        if (this.props.fileOnly) {
            return <img src={src} onClick={(e) => this.handleClick(e)} />
            // return <img src={URL.createObjectURL(src)} onClick={(e) => this.handleClick(e)} />
        } else {
            return <img src={API_HOST + src} onClick={(e) => this.handleClick(e)} />
        }
    }

    render() {
        let src = "";
        if (this.props.fileOnly) {
            src = this.props.src;
        } else if (this.props.src && this.props.src != '' && this.props.type == 'image') {
            src = this.props.src;
        }
        else if (this.props.placeholder && this.props.placeholder != '') {
            src = this.props.placeholder;
        }

        return (
            <div className="ant-upload-picture-card-wrapper avatar-uploader" id="singleFileUpload" >
                <div className="ant-upload ant-upload-select ant-upload-select-picture-card singleFileUpload" onClick={(e) => this.handleClick(e)}>
                    <span tabindex="0" class="ant-upload" role="button">
                        {src != null ?
                            this.displayImage(src)
                            :
                            <div>
                                <span role="img" aria-label="plus" class="anticon anticon-plus">
                                    <svg viewBox="64 64 896 896" focusable="false" data-icon="plus" width="1em" height="1em" fill="currentColor" aria-hidden="true"><defs><style></style></defs><path d="M482 152h60q8 0 8 8v704q0 8-8 8h-60q-8 0-8-8V160q0-8 8-8z"></path><path d="M176 474h672q8 0 8 8v60q0 8-8 8H176q-8 0-8-8v-60q0-8 8-8z"></path></svg>
                                </span>
                                <div>Upload</div>
                            </div>
                        }
                    </span>
                </div>
                <input ref={this.inputRef} className="hide" type="file"
                    onChange={(e) => this.handleUpload(e)} />
            </div>
        );
    }
}

export default FileUpload;