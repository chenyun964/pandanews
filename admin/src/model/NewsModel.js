import config from '../config/Config';
import axios from 'axios';

class NewsModel {
  async list() {
    return axios.get(config['news_api']);
  }
  
  async find(id) {
    return axios.get(config['news_api'] + "/" + id);
  } 

  async update(id) {
    return axios.put(config['news_api'] + "/" + id);
  } 

  async create(data) {
    return axios.post(config['news_api'], data);
  }
  
   async delete(id) {
    return axios.delete(config['news_api'] + "/" + id);
  } 

  async createByAPI() {
    return axios.post(config['news_generate_bing_api']);
  }
}


export default new NewsModel();
