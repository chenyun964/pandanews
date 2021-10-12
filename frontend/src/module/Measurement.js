import { Component } from 'react';
import MeasurementModel from "../model/MeasurementModel";
import React from 'react';
import "../App.css";
import"../index.js";

// import ReactDOM from "react-dom";
// import { Col, Row, Container } from "@kunukn/react-bootstrap-grid";

class Measurement extends Component {
    constructor(props) {
        super(props)
        this.state = {
            measurements: []
        };
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

    render() {
        return (
            <div className="meaInfographic">
                <header className="App-header">
                    <div className="App-intro">
                        <h2 style={{'padding-top':'0%'}}>Latest Measurements</h2>
                        {this.state.measurements.map((m, i) => {
                            return (<div key={m.id} class="grid-container-element">
                                    <div class="grid-child-element">
                                        <img style={{ "display": "inline-block" }} height="50px" src='https://react.semantic-ui.com/images/wireframe/image.png' />
                                        <h3 style={{ "color": "White", "display": "inline-block",'padding-left': '0.3cm'}}>{m.title}</h3>
                                        <p style={{ "size": "1pt", 'paddingRight':"0.5cm"}}>{m.content}</p>
                                    </div>
                            </div>
                            )
                        }
                        )}
                    </div>
                </header>
            </div>
        );
    }

}

export default Measurement;
