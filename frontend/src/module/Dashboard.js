import { Component } from 'react';
import LoginModel from '../model/LoginModel';

    class Dashboard extends Component {

        componentDidMount() {
            if (!LoginModel.retrieveToken()) {
                this.props.history.push("/login");
            }
        }

        render() {
            return (
                <div class="row">
                    <div className="col-12 text-center">
                        <h1>Hello!</h1>
                    </div>
                </div>
            );
        }
    }

export default Dashboard;

