import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class OrganisationModel {
  async create(data) {
    return axiosJwt.post(config['org_api'], data);
  }

  async join(data) {
    return axiosJwt.post(config['org_join_api'], data);
  }

  async myOrg() {
    return axiosJwt.get(config["org_my_org_api"]);
  }

  async employee() {
    return axiosJwt.get(config["org_employee_api"]);
  }

  async getByCode(code){
    return axiosJwt.get(config['org_api'] + "/" + code);
  }
}

export default new OrganisationModel();
