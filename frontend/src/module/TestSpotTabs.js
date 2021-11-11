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
                                <div className="row">
                                    <div className="col-12 col-lg-6">
                                        <TestSpotList type="PCR" />
                                    </div>
                                    <div className="col-12 col-lg-6">
                                        <TestSpotMap type="PCR" />
                                    </div>
                                </div>
                            </TabPane>
                            <TabPane tab="ART" key="2">
                                <div className="row">
                                    <div className="col-12 col-lg-6">
                                        <TestSpotList type="ART" />
                                    </div>
                                    <div className="col-12 col-lg-6">
                                        <TestSpotMap type="ART" />
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

export default TestSpotTabs;