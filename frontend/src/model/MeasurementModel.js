import axios from 'axios';
import config from '../config/Config';

class MeasurementModel {
    async list() {
        return axios.get(config['measurement_api']);
    }

}

export default new MeasurementModel();