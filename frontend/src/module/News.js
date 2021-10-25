import { Component } from 'react';
import NewsModel from '../model/NewsModel';
import CategoryModel from '../model/CategoryModel';
import { Link } from "react-router-dom";
import { Carousel } from 'antd';

const contentStyle = {
    height: '500px',
    color: '#fff',
    textAlign: 'center',
};

class News extends Component {
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
                                    <div class="carousel-caption d-none d-md-block">
                                        <h5>{news.title}</h5>
                                        <p>{news.description}</p>
                                    </div>
                                </div>
                            </div>
                        )
                    })}
                </Carousel>

                <div className="container-fuild p-5">
                    <div className="row">
                        <div className="col-8">
                            {this.state.news.map((news, i) => {
                                return (
                                    <a href={news.content} target="_blank">
                                        <div class="row mb-3" style={{ "border": "1px solid grey", "color": "black" }}>
                                            <div className="d-flex ">
                                                <img src={news.coverImage} alt="" height="100"></img>
                                                <div class="d-flex flex-column">
                                                    <div>{news.title}</div>
                                                    <div>{news.description}</div>
                                                    <div>{news.date}</div>
                                                    <div>View: {news.viewCount}</div>
                                                </div>

                                            </div>
                                        </div>
                                    </a>
                                )
                            })}
                        </div>
                        <div class="col-4">
                            <div>
                                
                            </div>
                            <div className="d-flex flex-wrap">
                                {this.state.category.map((category, i) => {
                                    return (
                                        <Link to={"/category/" + category.title} class="m-2 bd-highlight">{category.title}</Link>
                                    )
                                })}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

}

export default News;

