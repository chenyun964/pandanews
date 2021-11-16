import axios from "axios";
import config from '../config/Config';

class StatisticsModel {
    async list() {
        return axios.get(config['statistics_api']);
    }

    async summary(){
        return axios.get(config['statistics_api'] + "/summary");
    }
}

export default new StatisticsModel();
