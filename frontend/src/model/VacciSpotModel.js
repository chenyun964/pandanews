import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class VacciSpotModel {
  async getAll() {
    return axiosJwt.get(config['vaccispots_api']);
  }

  async getById(id) {
    return axiosJwt.get(config['vaccispots_api'] + '/' + id);
  }

  async getByName(name) {
    return axiosJwt.get(config['vaccispots_name_api'] + '/' + name);
  }

  async getByRegion(region) {
    return axiosJwt.get(config['vaccispots_region_api'] + '/' + region);
  }

  async getByType(type) {
    return axiosJwt.get(config['vaccispots_type_api'] + '/' + type);
  }

  async getByVacciType(vacciType) {
    return axiosJwt.get(config['vaccispots_vaccitype_api'] + '/' + vacciType);
  }

  async add(data) {
    return axiosJwt.post(config['vaccispots_api'], data);
  }

  async update(id, data) {
    return axiosJwt.put(config['vaccispots_api'] + '/' + id, data);
  }

  async delete(id) {
    return axiosJwt.delete(config['vaccispots_api'] + '/' + id);
  }
}

export default new VacciSpotModel();
