import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class UserModel {
    async profile() {
        return axiosJwt.get(config['user_profile_api']);
    }

    async saveProfile(data) {
        return axiosJwt.put(config['user_profile_api'], data);
    }

    async userOrg(){
        return axiosJwt.get(config["user_org_api"]);
    }
}

export default new UserModel();
