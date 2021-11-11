import axiosJwt from '../lib/AxiosJwt';
import config from '../config/Config';

const uri = "/policy"

class PolicyModel {
    
    async list(oid) {
        return axiosJwt.get(config['org_api'] + "/" + oid);
    }

    async get(oid, pid) {
        return axiosJwt.get(config['org_api'] + "/" + oid + uri + "/" + pid);
    }

    async create(oid, data) {
        return axiosJwt.post(config['org_api'] + "/" + oid + uri, data);
    }

    async update(oid, pid, data) {
        return axiosJwt.put(config['org_api'] + "/" + oid + uri + "/" + pid, data);
    }

    async delete(oid, pid) {
        return axiosJwt.delete(config['org_api'] + "/" + oid + uri + "/" + pid);
    }

}

export default new PolicyModel();
