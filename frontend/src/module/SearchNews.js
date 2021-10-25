import { Component, Fragment } from 'react';
import NewsModel from '../model/NewsModel';
import CategoryModel from '../model/CategoryModel';
import { Carousel, Input } from 'antd';
import { Link } from "react-router-dom";
import moment from 'moment';



const { Search } = Input;
class SearchNews extends Component {
    

    constructor(props) {
        super(props);
        this.state = {
            news: [],
            category: [],
            slug: this.props.match.params.keyword
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

       //NewsModel.list().then(res => {
        NewsModel.search_news(this.state.slug).then(res => {
            console.log("abc" + this.state.slug);
            this.setState({
                news: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    componentDidUpdate(prevProps) {
        if (prevProps.match.params.keyword !== this.props.match.params.keyword) {
            this.setState({
                slug: this.props.match.params.keyword,
            });
        }
    }
    onSearch(value) {
        window.location.replace("/search" + "/" + value);
        }
        
    render() {
        return (
            <Fragment>
                <div className="category-container text-center">
                    <h1 className="title">Keyword: {this.state.slug}</h1>
                </div>
                <div className="element-container">
                    <div className="container">
                        <div className="row">
                            <div className="col-lg-8 col-12">
                                {this.state.news.map((news, i) => {
                                    return (
                                        <a href={news.content} target="_blank">
                                            <div class="row mb-3 news-item">
                                                <div className="col-md-4 col-12 news-image" style={{ "backgroundImage": "url(" + news.coverImage + ")" }}></div>
                                                <div className="col-md-8 col-12 p-4">
                                                    <div className="news-cate">{news.category}</div>
                                                    <h5 className="news-title">{news.title.length < 105 ? news.title : news.title.slice(0, 100) + "..."}</h5>
                                                    <div className="news-info">{moment(news.date, "YYYY-MM-DD").format("MMM D, YY")} &#9679; {news.viewCount} Views</div>
                                                    <div className="news-descr">{news.description.length < 265 ? news.description : news.description.slice(0, 250) + "..."}</div>
                                                    <a className="news-read-btn" href={news.content} target="_blank">READ MORE <i class="fas fa-long-arrow-alt-right"></i></a>
                                                </div>
                                            </div>
                                        </a>
                                    )
                                })}
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

export default SearchNews;
