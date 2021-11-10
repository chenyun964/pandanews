import { Component } from 'react';

class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    componentDidMount() {
    }

    render() {
        return (
            <div class="content">
                <header class="page-header">
                    <div class="d-flex align-items-center">
                        <div class="mr-auto">
                            <h1>Dashboard</h1>
                        </div>
                        <ul class="actions top-right">
                            <li class="dropdown">
                                <a href="javascript:void(0)" class="btn btn-fab" data-toggle="dropdown" aria-expanded="false">
                                    <i class="la la-ellipsis-h"></i>
                                </a>
                                <div class="dropdown-menu dropdown-icon-menu dropdown-menu-right">
                                    <div class="dropdown-header">
                                        Quick Actions
                                    </div>
                                    <a href="#" class="dropdown-item">
                                        <i class="icon dripicons-clockwise"></i> Refresh
                                    </a>
                                    <a href="#" class="dropdown-item">
                                        <i class="icon dripicons-gear"></i> Manage Widgets
                                    </a>
                                    <a href="#" class="dropdown-item">
                                        <i class="icon dripicons-cloud-download"></i> Export
                                    </a>
                                    <a href="#" class="dropdown-item">
                                        <i class="icon dripicons-help"></i> Support
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </header>

                <section class="page-content container-fluid">
                    <div class="row">

                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-header">
                                    <span class="d-inline-block">Hi, </span>
                                </div>
                                <div class="card-body p-0">
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        );
    }
}

export default Dashboard;

