import config from '../config/Config';
import axios from 'axios';

class NewsModel {
  async list(data) {
    return axios.get(config['news_list_api']);
  }

  async list_top_4(data) {
    return axios.get(config['news_get_top_4_api']);
  }

  async list_category_news(data) {
    return axios.get(config['category__news_api']);
  }
}

export default new NewsModel();
