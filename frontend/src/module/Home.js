import { Component } from 'react';
import NewsModel from '../model/NewsModel';
import CategoryModel from '../model/CategoryModel';
import { Link } from "react-router-dom";
import { Carousel } from 'antd';
import Measurement from './Measurement';
import moment from 'moment';

const contentStyle = {
    height: '500px',
    color: '#fff',
    textAlign: 'center',
};

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: [],
            category: [],
            top_4_news: []
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

        NewsModel.list().then(res => {
            console.log(res.data);
            this.setState({
                news: res.data
            })
        }).catch(e => {
            console.log(e);
        })

        NewsModel.list_top_4().then(res => {
            console.log(res.data);
            this.setState({
                top_4_news: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    render() {
        return (
            <div>
                <Carousel autoplay>
                    {this.state.top_4_news.map((news, i) => {
                        return (
                            <div className="carousel-item" style={contentStyle}>
                                <div style={contentStyle}>
                                    <img className=" w-100" src={news.coverImage} alt=""></img>
                                    <a href={news.content} target="_blank">
                                        <div class="carousel-caption d-none d-md-block">
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
                                <div className="section-container">
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
            </div>
        );
    }

}

export default Home;

