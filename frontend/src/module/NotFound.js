import { Component } from 'react';
import { Link } from "react-router-dom";
import { Result } from 'antd';

class Demo extends Component {
    render() {
        return (
            <div className="flex-fill">
                <Result
                    status="404"
                    title="404"
                    subTitle="Sorry, the page you visited does not exist."
                    extra={<Link className="btn btn-primary" to="/">Back Home</Link>}
                />
            </div>
        );
    }
}

export default Demo;

