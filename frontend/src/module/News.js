import { Component } from 'react';
import NewsModel from '../model/NewsModel';
import CategoryModel from '../model/CategoryModel';
import { Link } from "react-router-dom";



class News extends Component {
    constructor(props) {
        super(props);
        this.state = {
            news: [],
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

        NewsModel.list().then(res => {
            console.log(res.data);
            this.setState({
                news: res.data
            })
        }).catch(e => {
            console.log(e);
        })
    }

    render() {
        return (
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
        );
    }

}

export default News;

