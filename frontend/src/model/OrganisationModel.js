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

  async policy() {
    return axiosJwt.get(config["org_policy_api"]);
  }

  async workGroup() {
    return axiosJwt.get(config["org_workgroup_api"]);
  }

  async activate(id) {
    return axiosJwt.get(config["org_policy_activate_api"] + "/" + id);
  }

  async deactivate(id) {
    return axiosJwt.get(config["org_policy_activate_api"] + "/" + id);
  }

  async getByCode(code) {
    return axiosJwt.get(config['org_api'] + "/" + code);
  }

  async delete(id) {
    return axiosJwt.delete(config['org_api'] + "/" + id);
  }

  async promote(id) {
    return axiosJwt.put(config['org_employee_promote_api'] + "/" + id);
  }

  async demote(id) {
    return axiosJwt.put(config['org_employee_demote_api'] + "/" + id);
  }

  async remove(id) {
    return axiosJwt.delete(config['org_employee_api'] + "/" + id);
  }
}

export default new OrganisationModel();
