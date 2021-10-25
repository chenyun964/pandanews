import config from '../config/Config';
import axios from 'axios';

class NewsModel {
  async list(data) {
    return axios.get(config['news_list_api']);
  }
  
  async find(data) {
    return axios.get(config['news_find_api']);
  } 

  async update(data) {
    return axios.get(config['news_update_api']);
  } 

  async create(data) {
    return axios.get(config['news_create_api']);
  }
  
   async delete(data) {
    return axios.get(config['news_delete_api']);
  } 

  async delete(data) {
    return axios.get(config['news_generate_bing_api']);
  }
}


export default new NewsModel();
