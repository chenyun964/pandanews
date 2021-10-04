import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class OrganisationModel {
  async create(data) {
    return axiosJwt.post(config['org_create_api'], data);
  }

  async join(data) {
    return axiosJwt.post(config['org_join_api'], data);
  }

  async myOrg() {
    return axiosJwt.get(config["org_my_org_api"]);
  }
}

export default new OrganisationModel();
