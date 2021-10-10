import { Component } from 'react';
import { Tabs } from 'antd';
import VacciSpotList from './VacciSpotList';
import Map from './Map';

const { TabPane } = Tabs;

class VacciSpotTabs extends Component {
    render() {
        return (
            <div>
                <Tabs defaultActiveKey="1" size="large" style={{ marginLeft: 16, marginRight: 16 }}>
                    <TabPane tab="Vaccination Centres" key="1">
                        <VacciSpotList type="Vaccination Centre" />
                        <Map type="Vaccination Centre" />
                    </TabPane>
                    <TabPane tab="Clinics" key="2">
                        <VacciSpotList type="Clinic" />
                        <Map type="Clinic" />
                    </TabPane>
                    <TabPane tab="Polyclinics" key="3">
                        <VacciSpotList type="Polyclinic" />
                        <Map type="Polyclinic" />
                    </TabPane>
                </Tabs>
            </div>
        )
    }
}

export default VacciSpotTabs;