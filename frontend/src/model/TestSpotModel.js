import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class TestSpotModel {
  async getAll() {
    return axiosJwt.get(config['testspots_api']);
  }

  async getById(id) {
    return axiosJwt.get(config['testspots_api'] + '/' + id);
  }

  async getByName(name) {
    return axiosJwt.get(config['testspots_name_api'] + '/' + name);
  }

  async getByType(type) {
    return axiosJwt.get(config['testspots_type_api'] + '/' + type);
  }

  async add(data) {
    return axiosJwt.post(config['testspots_api'], data);
  }

  async update(id, data) {
    return axiosJwt.put(config['testspots_api'] + '/' + id, data);
  }

  async delete(id) {
    return axiosJwt.delete(config['testspots_api'] + '/' + id);
  }
}

export default new TestSpotModel();
