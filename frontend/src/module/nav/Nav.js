import React, { Component } from 'react';
import CategoryModel from '../../model/CategoryModel';

class Nav extends Component {
  constructor(props) {
    super(props);
    this.state = {
      category: []
    }
  }

  componentDidMount() {
    CategoryModel.list().then(res => {
      this.setState({
        category: res.data
      })
    }).catch(e => {
      console.log(e);
    })
  }

  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light head-navbar">
        <div className="container-fluid">
          <a className="navbar-brand" href="/">
            <img src="../img/logo.png" alt="" height="30" />
          </a>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            </ul>
            <ul className="d-flex navbar-nav mb-2 mb-lg-0">
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="/">Home</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="/covid19">COVID-19</a>
              </li>
              <li className="nav-item dropdown">
                <button className="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Categories
                </button>
                <ul className="dropdown-menu dropdown-menu-right cate-dropdown" aria-labelledby="navbarDropdown">
                  {this.state.category.map((category, i) => {
                    return (
                      <li><a className="dropdown-item" href={"/category/" + category.title}>{category.title}</a></li>
                    )
                  })}

                </ul>
              </li>
              <li className="nav-item dropdown">
                <button className="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Map
                </button>
                <ul className="dropdown-menu cate-dropdown dropdown-menu-right" aria-labelledby="navbarDropdown">
                  <li><a className="dropdown-item" href={"/vaccispots"}>Vaccination Spots</a></li>
                  <li><a className="dropdown-item" href={"/testspots"}>Swab Test Spots</a></li>
                </ul>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="/login"><i className="far fa-user"></i></a>
              </li>

            </ul>
          </div>
        </div>
      </nav>
    );
  }
}

export default Nav;
