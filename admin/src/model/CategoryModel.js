import config from '../config/Config';
import axios from 'axios';

class CategoryModel {
  async list() {
    return axios.get(config['category_api']);
  }
  
  async find(id) {
    return axios.get(config['category_api'] + "/" + id);
  } 

  async update(id, data) {
    return axios.put(config['category_api'] + "/" + id, data);
  } 

  async create(data) {
    return axios.post(config['category_api'], data);
  }
  
   async delete(id) {
    return axios.delete(config['category_api'] + "/" + id);
  } 
}


export default new CategoryModel();
