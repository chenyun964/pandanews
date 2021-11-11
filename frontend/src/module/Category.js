import { Component, Fragment } from 'react';
import NewsModel from '../model/NewsModel';
import CategoryModel from '../model/CategoryModel';
import { Link } from "react-router-dom";
import moment from 'moment';
import { Result, Input } from 'antd';
const { Search } = Input;

class Category extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: [],
            category: [],
            slug: this.props.match.params.category
        }
    }

    componentDidMount() {
        this.listCate();
        this.getNews();
    }

    componentDidUpdate(prevProps) {
        if (prevProps.match.params.category !== this.props.match.params.category) {
            this.setState({
                slug: this.props.match.params.category,
            }, () => {
                this.listCate();
                this.getNews();
            });
        }
    }

    listCate() {
        CategoryModel.list().then(res => {
            this.setState({
                category: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    getNews() {
        NewsModel.listCategory(this.state.slug).then(res => {
            console.log(this.state.slug);
            this.setState({
                news: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    onSearch(value) {
        window.location.replace("/search" + "/" + value);
    }

    increaseCount(news) {
        let url = news.source === "Manual" ? "/news/" + news.slug : news.content;
        NewsModel.updateCount(news.slug).then(res => {
            window.open(url, '_blank').focus();
        }).catch(e => {
            console.log(e);
            window.open(url, '_blank').focus();
        })
    }

    render() {
        return (
            <Fragment>
                <div className="category-container text-center">
                    <h1 className="title">Category: {this.state.slug}</h1>
                </div>
                <div className="element-container">
                    <div className="container">
                        <div className="row">
                            <div className="col-lg-8 col-12">
                                {this.state.news.length > 0 ?
                                    <Fragment>
                                        {this.state.news.map((news, i) => {
                                            return (
                                                <button className="mb-3 news-card-btn" onClick={() => this.increaseCount(news)}>
                                                    <div class="row news-item">
                                                        <div className="col-md-4 col-12 news-image" style={{ "backgroundImage": "url(" + news.coverImage + ")" }}></div>
                                                        <div className="col-md-8 col-12 p-4">
                                                            <div className="news-cate">{news.category.title}</div>
                                                            <h5 className="news-title">{news.title.length < 105 ? news.title : news.title.slice(0, 100) + "..."}</h5>
                                                            <div className="news-info">{moment(news.date, "YYYY-MM-DD").format("MMM D, YY")} &#9679; {news.viewCount} Views</div>
                                                            <div className="news-descr">{news.description.length < 265 ? news.description : news.description.slice(0, 250) + "..."}</div>
                                                            <a className="news-read-btn" href={news.content} target="_blank">READ MORE <i class="fas fa-long-arrow-alt-right"></i></a>
                                                        </div>
                                                    </div>
                                                </button>
                                            )
                                        })}
                                    </Fragment>
                                    :
                                    <Result
                                        status="404"
                                        title="404"
                                        subTitle="No news belongs to this category"
                                    />
                                }
                            </div>
                            <div class="col-lg-4 col-12">
                                <div className="section-container mb-4">
                                    <Search placeholder="Search for news..." onSearch={(value) => this.onSearch(value)} enterButton />
                                </div>

                                <div className="section-container mb-4">
                                    <h4>Categories</h4>
                                    <div className="d-flex flex-wrap">
                                        {this.state.category.map((category, i) => {
                                            return (
                                                <Link to={"/category/" + category.title} class="badge rounded-pill cate-badge">{category.title}</Link>
                                            )
                                        })}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Fragment>
        );
    }

}

export default Category;

