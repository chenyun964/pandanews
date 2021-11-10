import config from '../config/Config';
import axios from 'axios';

class CategoryModel {
  async list() {
    return axios.get(config['category_api']);
  }
}

export default new CategoryModel();
