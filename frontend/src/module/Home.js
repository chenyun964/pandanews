import { Component } from 'react';
import NewsModel from '../model/NewsModel';
import CategoryModel from '../model/CategoryModel';
import { Link } from "react-router-dom";
import { Carousel, Input } from 'antd';
import Measurement from './Measurement';
import moment from 'moment';
import StatisticsModel from '../model/StatisticsModel';
import { Pagination } from 'antd';

const { Search } = Input;
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: [],
            category: [],
            pageNumber: 1,
            top4News: [],
            covidSummary: {}
        }
    }

    componentDidMount() {
        this.listTop4();
        this.listNews();
        this.listCategory();
        this.listSummary();
    }

    listNews() {
        NewsModel.list().then(res => {
            console.log(res.data);
            this.setState({
                news: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    listCategory() {
        CategoryModel.list().then(res => {
            this.setState({
                category: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    listTop4() {
        NewsModel.listTop4().then(res => {
            this.setState({
                top4News: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    listSummary() {
        StatisticsModel.summary().then(res => {
            console.log(res.data);
            this.setState({
                covidSummary: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    onSearch(value) {
        window.location.replace("/search" + "/" + value);
    }

    newsPageOnchange(e){
        this.setState({
            pageNumber: e
        })
    }

    render() {
        return (
            <div>
                <Carousel>
                    {this.state.top4News.map((news, i) => {
                        return (
                            <div key={i} className="carousel-item news-image">
                                <div class="img-overlay"></div>
                                <div className="carousel-item-image" style={{ "backgroundImage": "url(" + news.coverImage + ")" }}>
                                    <a href={news.content} target="_blank">
                                        <div className="carousel-caption">
                                            <h5>{news.title}</h5>
                                            <p>{news.description}</p>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        )
                    })}
                </Carousel>

                <Measurement />
                <div className="element-container">
                    <div className="container">
                        <div className="row">
                            <div className="col-lg-8 col-12 order-lg-first order-last">
                                {this.state.news.map((news, i) => {
                                    if (i >= (this.state.pageNumber - 1) * 10 && i < this.state.pageNumber * 10) {
                                        return (
                                            <a key={i} href={news.content} target="_blank">
                                                <div className="row mb-3 news-item">
                                                    <div className="col-md-4 col-12 news-image" style={{ "backgroundImage": "url(" + news.coverImage + ")" }}></div>
                                                    <div className="col-md-8 col-12 p-4">
                                                        <div className="news-cate">{news.category ? news.category.title : ""}</div>
                                                        <h5 className="news-title">{news.title.length < 105 ? news.title : news.title.slice(0, 100) + "..."}</h5>
                                                        <div className="news-info">{moment(news.date, "YYYY-MM-DD").format("MMM D, YY")} &#9679; {news.viewCount} Views</div>
                                                        <div className="news-descr">{news.description.length < 265 ? news.description : news.description.slice(0, 250) + "..."}</div>
                                                        <div className="news-read-btn" href={news.content} target="_blank">READ MORE <i className="fas fa-long-arrow-alt-right"></i></div>
                                                    </div>
                                                </div>
                                            </a>
                                        )
                                    }
                                })}
                                <Pagination defaultCurrent={this.state.pageNumber} onChange={(e) => this.newsPageOnchange(e)} total={this.state.news.length} />
                            </div>

                            <div className="col-lg-4 col-12 order-lg-last order-first">
                                <div className="section-container mb-4">
                                    <Search placeholder="Search for news..." onSearch={(value) => this.onSearch(value)} enterButton />
                                </div>

                                <div className="section-container mb-4">
                                    <h4>Covid-19 Cases</h4>
                                    <div className="covid-case-update">Updated at {moment(this.state.covidSummary.lastedRecord?.updatedAt, "YYYY-MM-DD hh:mm:ss").format("hA, DD MMM YYYY")}</div>
                                    <div className="d-flex flex-column text-center">
                                        <div className="d-flex flex-column covid-case covid-case-danger">
                                            <div className="label">Total Cases</div>
                                            <div className="result">{this.state.covidSummary.totalCases}</div>
                                        </div>
                                        <div className="d-flex flex-column covid-case covid-case-success">
                                            <div className="label">Total Recovery</div>
                                            <div className="result">{this.state.covidSummary.totalRecovery}</div>
                                        </div>
                                        <div className="d-flex flex-column covid-case covid-case-default">
                                            <div className="label">Total Death</div>
                                            <div className="result">{this.state.covidSummary.totalDeath}</div>
                                        </div>
                                    </div>
                                </div>

                                <div className="section-container mb-4">
                                    <h4>Categories</h4>
                                    <div className="d-flex flex-wrap">
                                        {this.state.category.length > 0 ?
                                            this.state.category.map((category, i) => {
                                                return (
                                                    <Link key={i} to={"/category/" + category.title} className="badge rounded-pill cate-badge">{category.title}</Link>
                                                )
                                            })
                                            :
                                            <div> No Categories </div>
                                        }
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        );
    }

}

export default Home;

