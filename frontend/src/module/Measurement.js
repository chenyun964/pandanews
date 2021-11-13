import { Component } from 'react';
import MeasurementModel from "../model/MeasurementModel";

class Measurement extends Component {
    constructor(props) {
        super(props)
        this.state = {
            measurements: []
        };
    }

    componentDidMount() {
        MeasurementModel.list().then(res => {
            this.setState({
                measurements: res.data
            })
        }).catch(e => {
            console.log(e)
        })
    }

    render() {
        return (
            <div className="element-container mea-container">
                <div className="container">
                    <div className="row">
                        <div className="col-12">
                            <div className="text-center">
                                <h3 className="title mea-subtitle"> Latest Measurements </h3>
                            </div>
                            <div className="row">
                                {this.state.measurements.map((m, i) => {
                                    return (
                                        <div key={i} className="col-lg-4 col-md-6 mea-item mb-3">
                                            <div className="d-flex">
                                                <img src={m.imageUrl} height="50" />
                                                <div className="d-flex flex-column">
                                                    <strong>{m.title}</strong>
                                                    <div dangerouslySetInnerHTML={{ __html: m.content }} />
                                                </div>
                                            </div>
                                        </div>
                                    )
                                }
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Measurement;
