import axiosJWT from '../lib/AxiosJwt';
import config from '../config/Config';

class OrganisationModel {
  async list(data) {
    return axiosJWT.get(config['org_api'], data);
  }
}

export default new OrganisationModel();
