import './App.css';
import React, { Component, Fragment } from 'react';
import Routes from './routes.js';
import 'antd/dist/antd.css';

class App extends Component {
  render() {
    return (
      <Fragment>
        <div className="App" id="app">
          <Routes/>
        </div>
      </Fragment>
    );
  }
}

export default App;
