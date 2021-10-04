import { Component } from 'react';
import MeasurementModel from "../model/MeasurementModel";
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
        //const {measurements} = this.state;
        return (
            <div className="meaInfographic">
                <header className="App-header">
                    <div className="App-intro">
                        <h2>Latest Measurements</h2>
                        {this.state.measurements.map((m, i) =>{
                            return (<div key={m.id}>
                                {m.title}
                                {m.imageUrl}
                                ({m.content})
                                </div>
                            )}
                  )}
                    </div>
                </header>
            </div>
        );
    }

}

export default Measurement;
