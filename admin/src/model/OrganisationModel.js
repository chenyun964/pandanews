import axiosJWT from '../lib/AxiosJwt';
import config from '../config/Config';

class OrganisationModel {
  async list(data) {
    return axiosJWT.get(config['org_api'], data);
  }

  async approve(id) {
    return axiosJWT.put(config['org_approve_api'] + "/" + id);
  }

  async delete(id) {
    return axiosJWT.delete(config['org_api'] + "/" + id);
  }
}

export default new OrganisationModel();
