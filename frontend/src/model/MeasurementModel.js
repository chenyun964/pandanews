import axios from 'axios';
import config from '../config/Config';

class MeasurementModel{
    async createMea(data) {
        return axios.post(config['mea_create_api'], data);
      }
      
    async mList() {
        return axios.get(config['measurement_api']);
    }
}

export default new MeasurementModel();