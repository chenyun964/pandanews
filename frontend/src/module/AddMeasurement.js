import { Component } from 'react';
import MeasurementModel from "../model/MeasurementModel";
import React from 'react';
import "../App.css";
import"../index.js";

// import ReactDOM from "react-dom";
// import { Col, Row, Container } from "@kunukn/react-bootstrap-grid";

class AddMeasurement extends Component {
    constructor(props) {
        super(props)
        this.state = {
            fields: {
                "title":"",
                "imageUrl":"",
                "content":""
            }
        };
    }

    handleValidation() {
        let fields = this.state.fields;
        let errors = {};
        let formIsValid = true;

        if (!fields["title"]) {
            formIsValid = false;
            errors["title"] = "title cannot be empty";
        }

        if (!fields["imageUrl"]) {
            formIsValid = false;
            errors["imageUrl"] = "imageUrl cannot be empty";
        }

        if (!fields["content"]) {
            formIsValid = false;
            errors["content"] = "content cannot be empty";
        }

        this.setState({ errors: errors });
        return formIsValid;
    }


    handleChange(field, e) {
        let fields = this.state.fields;
        fields[field] = e.target.value;
        this.setState({ fields });
    }

    componentDidMount() {
        MeasurementModel.mList().then(res => {
            this.setState({
                measurements: res.data
            })
        }).catch(e => {
            console.log(e)
        })
    }

    renderForm() {
        return this.state.added ?
            <div className="d-flex align-items-center justify-content-center p-2 text-center flex-column" style={{ "height": "100%" }}>
                <p>Congratulation, you have successfully add a measurement!</p>
            </div>
            :
            <div className="d-flex flex-column p-5">
                <div className="text-center">
                    <h1 className="section-title pt-2 pb-4">Measurement Updated</h1>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" placeholder="title" ref="title" onChange={this.handleChange.bind(this, "title")}
                        value={this.state.fields["title"]} />
                    <span className="input-error-msg">{this.state.errors["title"]}</span>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" placeholder="imageUrl" ref="imageUrl" onChange={this.handleChange.bind(this, "imageUrl")}
                        value={this.state.fields["imageUrl"]} />
                    <span className="input-error-msg">{this.state.errors["imageUrl"]}</span>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" placeholder="content" ref="content" onChange={this.handleChange.bind(this, "content")}
                        value={this.state.fields["content"]} />
                    <span className="input-error-msg">{this.state.errors["content"]}</span>
                </div>

                <div className="text-center">
                    <button className="btn btn-primary" onClick={() => this.componentDidMount()()}>Add Measurement</button>
                </div>
                <hr />
            </div>
    }

}

export default AddMeasurement;