import axios from 'axios';
import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class MeasurementModel{
    async add(data) {
        return axiosJwt.post(config['mea_create_api'], data);
    }

    async update(id, data) {
        return axiosJwt.put(config['mea_create_api'] + '/' + id, data);
    }
      
    async mList() {
        return axios.get(config['measurement_api']);
    }

    async delete(id) {
        return axiosJwt.delete(config['mea_create_api'] + '/' + id);
    }
}

export default new MeasurementModel();