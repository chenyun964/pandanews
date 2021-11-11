import config from '../config/Config';
import axios from 'axios';
import axiosJwt from '../lib/AxiosJwt';

class NewsModel {
  async list() {
    return axios.get(config['news_api']);
  }
  
  async find(id) {
    return axios.get(config['news_api'] + "/" + id);
  } 

  async update(id, data) {
    return axios.put(config['news_api'] + "/" + id, data);
  } 

  async create(data) {
    return axios.post(config['news_api'], data);
  }
  
   async delete(id) {
    return axios.delete(config['news_api'] + "/" + id);
  } 

  async createByAPI() {
    return axiosJwt.post(config['news_generate_bing_api']);
  }
}


export default new NewsModel();
