import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

class WorkGroupModel {
  async create(organisationid, data) {
    return axiosJwt.post(config['workgroup_api'] + "/" + organisationid + "/workgroup", data);
  }

  async delete(organisationid, id) {
    return axiosJwt.delete(config['workgroup_api'] + "/" + organisationid + "/workgroup", data);
  }
  
  async join(data) {
    return axiosJwt.post(config['workgroup_join_api'], data);
  }

  async remove(id) {
    return axiosJwt.delete(config['workgroup_api'] + "/" + id);
  }
}