import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class UserModel {
    async profile() {
        return axiosJwt.get(config['user_profile_api']);
    }

    async save(data) {
        return axiosJwt.put(config['user_profile_api'], data);
    }
}

export default new UserModel();