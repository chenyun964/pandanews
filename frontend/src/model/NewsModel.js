import config from '../config/Config';
import axios from 'axios';

class NewsModel {
  async list(data) {
    return axios.get(config['news_list_api']);
  }

  async list_top_4(data) {
    return axios.get(config['news_get_top_4_api']);
  }

  async list_category_news(title) {
    return axios.get(config['category_news_api'] + '/' + title);
  }

  async search_news(keyword) {
    return axios.get(config['search_news_api'] + '/' + keyword);
  }
}

export default new NewsModel();
