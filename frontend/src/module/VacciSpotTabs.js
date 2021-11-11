import { Component } from 'react';
import { Tabs } from 'antd';
import VacciSpotList from './VacciSpotList';
import VacciSpotMap from './VacciSpotMap';

const { TabPane } = Tabs;

class VacciSpotTabs extends Component {
    render() {
        return (
            <div>
                <div className="p-5 flex-fill">
                    <div className="flex-fill">
                        <h1>Vaccination Spots</h1>
                    </div>
                    <div className="flex-fill">
                        <Tabs defaultActiveKey="1" size="large">
                            <TabPane tab="Vaccination Centres" key="1">
                                <div className="row">
                                    <div className="col-12 col-lg-6">
                                        <VacciSpotList type="Vaccination Centre" />
                                    </div>
                                    <div className="col-12 col-lg-6">
                                        <VacciSpotMap type="Vaccination Centre" />
                                    </div>
                                </div>
                            </TabPane>
                            <TabPane tab="Polyclinics" key="2">
                                <div className="row">
                                    <div className="col-12 col-lg-6">
                                        <VacciSpotList type="Polyclinic" />
                                    </div>
                                    <div className="col-12 col-lg-6">
                                        <VacciSpotMap type="Polyclinic" />
                                    </div>
                                </div>
                            </TabPane>
                            <TabPane tab="Clinics" key="3">
                                <div className="row">
                                    <div className="col-12 col-lg-6">
                                        <VacciSpotList type="Clinic" />
                                    </div>
                                    <div className="col-12 col-lg-6">
                                        <VacciSpotMap type="Clinic" />
                                    </div>
                                </div>
                            </TabPane>
                        </Tabs>
                    </div>
                </div>
            </div>
        )
    }
}

export default VacciSpotTabs;