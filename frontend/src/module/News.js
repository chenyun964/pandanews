import { Component } from 'react';
import NewsModel from '../model/NewsModel';
import CategoryModel from '../model/CategoryModel';
import { Link } from "react-router-dom";
import StatisticsModel from '../model/StatisticsModel';
import moment from "moment";
import { Input, Result } from 'antd';

const { Search } = Input;

class News extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: null,
            category: [],
            top4News: [],
            covidSummary: {}
        }
    }

    componentDidMount() {
        if (this.props.match.params.slug) {
            NewsModel.slug(this.props.match.params.slug).then(res => {
                this.setState({
                    news: res.data,
                })
            }).catch(e => {
                console.log(e);
            })
        }
        this.listTop4();
        this.listCategory();
        this.listSummary();
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

    render() {
        return (
            <div className="element-container">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-8 col-12 order-lg-first">
                            {this.state.news ?
                                <div className="row mb-3 news-item">
                                    <div className="col-12 news-image" style={{ "backgroundImage": "url(" + this.state.news.coverImage + ")" }}></div>
                                    <div className="col-md-8 col-12 p-4">
                                        <div className="news-cate"><Link to={"/category/" + this.state.news.category.title} className="badge rounded-pill cate-badge">{this.state.news.category?.title}</Link></div>
                                        <h1 className="news-title">{this.state.news.title}</h1>
                                        <div className="news-info">{moment(this.state.news.date, "YYYY-MM-DD").format("MMM D, YY")} &#9679; {this.state.news.viewCount} Views</div>
                                        <div className="news-descr">{this.state.news.description}</div>
                                        <div className="news-content"><div dangerouslySetInnerHTML={{ __html: this.state.news.content }}></div></div>
                                    </div>
                                </div>
                                :
                                <Result
                                    status="404"
                                    title="404"
                                    subTitle="News is not here :("
                                />
                            }
                        </div>

                        <div className="col-lg-4 col-12 order-lg-last">
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
                                        <div className="text-center"> No Categories </div>
                                    }
                                </div>
                            </div>

                            <div className="section-container mb-4">
                                <h4>What's How</h4>
                                <div className="d-flex flex-wrap">
                                    {this.state.top4News.length > 0 ?
                                        this.state.top4News.map((news, i) => {
                                            return (
                                                <a key={i} href={news.source === "Manual" ? "/news/" + news.slug : news.content} target="_blank">
                                                    <div className="row mb-3 side-news-item">
                                                        <div className="col-md-4 col-12 news-image" style={{ "backgroundImage": "url(" + news.coverImage + ")" }}></div>
                                                        <div className="col-md-8 col-12 p-4">
                                                            <h5 className="news-title">{news.title.length < 40 ? news.title : news.title.slice(0, 40) + "..."}</h5>
                                                            <div className="news-info">{moment(news.date, "YYYY-MM-DD").format("MMM D, YY")} &#9679; {news.viewCount} Views</div>
                                                        </div>
                                                    </div>
                                                </a>
                                            )
                                        })
                                        :
                                        <div className="text-center"> No News Now </div>
                                    }
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

}

export default News;

