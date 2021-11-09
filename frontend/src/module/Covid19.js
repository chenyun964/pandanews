import { Component, Fragment } from 'react';
import StatisticsModel from '../model/StatisticsModel';

class Covid19 extends Component {
    constructor(props) {
        super(props);
        this.state = {
            covidSummary: {}
        }
    }

    componentDidMount(){
        StatisticsModel.summary().then(res =>{
            this.setState({
                covidSummary: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    render() {
        return (
            <div>
                <iframe src="https://d209m3w127yzkd.cloudfront.net/index.html" className="covid-case-map"/>
            </div>

        )
    }
}

export default Covid19;
