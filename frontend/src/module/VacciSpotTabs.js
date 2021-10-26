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
                                <VacciSpotList type="Vaccination Centre" />
                                <VacciSpotMap type="Vaccination Centre" />
                            </TabPane>
                            <TabPane tab="Clinics" key="2">
                                <VacciSpotList type="Clinic" />
                                <VacciSpotMap type="Clinic" />
                            </TabPane>
                            <TabPane tab="Polyclinics" key="3">
                                <VacciSpotList type="Polyclinic" />
                                <VacciSpotMap type="Polyclinic" />
                            </TabPane>
                        </Tabs>
                    </div>
                </div>
            </div>
        )
    }
}

export default VacciSpotTabs;