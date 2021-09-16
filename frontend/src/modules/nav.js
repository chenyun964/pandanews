import React, { Component } from 'react';

class Nav extends Component {
  render() {
    return (
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
          <a class="navbar-brand" href="#">
            <img src="../img/logo.png" alt="" height="30" />
          </a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            </ul>
            <ul class="d-flex navbar-nav mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">COVID-19</a>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Categories
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li><a class="dropdown-item" href="#">F&B</a></li>
                </ul>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Support</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#"><i class="fas fa-search"></i></a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#"><i class="far fa-bell"></i></a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/login"><i class="far fa-user"></i></a>
              </li>

            </ul>
          </div>
        </div>
      </nav>
    );
  }
}

export default Nav;
