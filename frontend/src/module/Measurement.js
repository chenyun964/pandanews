import { Component } from 'react';
import MeasurementModel from "../model/MeasurementModel";

import React from 'react';
import {
    Header,
    Item
} from 'semantic-ui-react';
import "../App.css";

const style = {
    h3: {
      marginTop: '2em',
      padding: '2em 0em',
    }
  }
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
                        <Header as='h3' content='Latest Measurements' style={style.h3} textAlign='center' />
                        {this.state.measurements.map((m, i) => {
                            return (<div key={m.id}>
                                <Item.Group>
                                    <Item>
                                    <Item.Image size='small' height="150px" src='https://react.semantic-ui.com/images/wireframe/image.png' />

                                    <Item.Content>
                                        <Item.Header as='a' >{m.title}</Item.Header>
                                        <Item.Description >
                                        <p>{m.content}</p>
                                        </Item.Description>
                                    </Item.Content>
                                    </Item>
                                </Item.Group>
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
