import { Component } from "react";
import { Collapse } from "antd";

class ViewPolicy extends Component {

    constructor(props) {
        super(props);
        this.state = {
            policies: []
        }
    }

    componentDidMount() {

    }

    render() {
        return (
            <Collapse defaultActiveKey={['1']}>
                {this.state.policies.map((i, p) => {
                    <Panel header="This is panel header 1" key="1">
                        <p>1</p>
                    </Panel>
                })}
            </Collapse>
        )
    }
}

export default ViewPolicy;