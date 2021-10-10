import config from '../config/Config';
import axios from 'axios';

class CategoryModel {
  async list(data) {
    return axios.get(config['category_list_api']);
  }
}

export default new CategoryModel();
