import { Component, Fragment } from 'react';
import { Tabs } from 'antd';
import TestSpotList from './TestSpotList';
import TestSpotMap from './TestSpotMap';

const { TabPane } = Tabs;

class TestSpotTabs extends Component {
    render() {
        return (
            <div>
                <div className="p-5 flex-fill">
                    <div className="flex-fill">
                        <h1>Swab Test Spots</h1>
                    </div>
                    <div className="flex-fill">
                        <Tabs defaultActiveKey="1" size="large">
                            <TabPane tab="PCR" key="1">
                                <TestSpotList type="PCR" />
                                <TestSpotMap type="PCR" />
                            </TabPane>
                            <TabPane tab="ART" key="2">
                                <TestSpotList type="ART" />
                                <TestSpotMap type="ART" />
                            </TabPane>
                        </Tabs>
                    </div>
                </div>
            </div>
        )
    }
}

export default TestSpotTabs;