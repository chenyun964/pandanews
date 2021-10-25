import config from '../config/Config';
import axios from 'axios';
import AxiosJwt from '../lib/AxiosJwt';

class AttendanceModel {
    async list(data) {
        return AxiosJwt.post(config['mark_attendance_api'], data);
    }
}

export default new AttendanceModel();
