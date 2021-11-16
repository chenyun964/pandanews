import axios from 'axios';
import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class MeasurementModel{
    async add(data) {
        return axiosJwt.post(config['measurement_api'], data);
    }

    async update(id, data) {
        return axiosJwt.put(config['measurement_api'] + '/' + id, data);
    }
      
    async list() {
        return axios.get(config['measurement_api']);
    }

    async delete(id) {
        return axiosJwt.delete(config['measurement_api'] + '/' + id);
    }

    async get(id){
        return axios.get(config['measurement_api'] + "/" + id);
    }
}

export default new MeasurementModel();