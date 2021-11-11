import config from '../config/Config';
import axios from 'axios';

class NewsModel {
  async list() {
    return axios.get(config['news_list_api']);
  }

  async listTop4() {
    return axios.get(config['news_get_top_4_api']);
  }

  async listCategory(title) {
    return axios.get(config['category_news_api'] + '/' + title);
  }

  async searchNews(keyword) {
    return axios.get(config['search_news_api'] + '/' + keyword);
  }
}

export default new NewsModel();
