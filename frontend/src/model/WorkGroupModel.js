import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class WorkGroupModel {
  async create(organisationid, data) {
    return axiosJwt.post(config['workgroup_api'] + "/" + organisationid + "workgroups", data);
  }

  async join(data) {
    return axiosJwt.post(config['workgroup_join_api'], data);
  }

  async delete(organisationid, id) {
    return axiosJwt.delete(config['workgroup_api'] + "/" + organisationid + "workgroups", data);
  }
}