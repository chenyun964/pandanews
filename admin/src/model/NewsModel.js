import config from '../config/Config';
import axios from 'axios';

class NewsModel {
  async list(data) {
    return axios.get(config['news_list_api']);
  }
}

export default new NewsModel();
