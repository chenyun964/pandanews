import React, { Component } from 'react';
import { NavLink } from "react-router-dom";
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
      console.log(res.data);
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
                <a className="nav-link dropdown-toggle" href="javascript:void(0)" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Categories
                </a>
                <ul className="dropdown-menu cate-dropdown" aria-labelledby="navbarDropdown">
                  {this.state.category.map((category, i) => {
                    return (
                      <li><a className="dropdown-item" href={"/category/" + category.title}>{category.title}</a></li>
                    )
                  })}

                </ul>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="/vaccispots">Support</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="/search"><i className="fas fa-search"></i></a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="/notficiation"><i className="far fa-bell"></i></a>
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
