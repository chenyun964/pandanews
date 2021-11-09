import axiosJWT from '../lib/AxiosJwt';
import config from '../config/Config';

class StatisticsModel {
    async list() {
        return axiosJWT.get(config['statistics_api']);
    }

    async create(data) {
        return axiosJWT.post(config['statistics_api'], data);
    }

    async update(id, data) {
        return axiosJWT.put(config['statistics_api'] + "/" + id,  data);
    }

    async delete(id) {
        return axiosJWT.delete(config['statistics_api'] + "/" + id);
    }

    async summary(){
        return axiosJWT.get(config['statistics_api'] + "/summary");
    }
}

export default new StatisticsModel();
