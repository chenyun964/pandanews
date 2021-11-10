import config from '../config/Config';
import axios from 'axios';
import AxiosJwt from '../lib/AxiosJwt';

class AttendanceModel {
    async markAttendance() {
        return AxiosJwt.post(config['attendance_api']);
    }

    async getAttendanceByDate(date) {
        return AxiosJwt.get(config['attendance_date_api'], { params: {date: date} });
    }

    async getAttendanceByUser(id) {
        return AxiosJwt.post(config['attendance_user_api'] + '/' + id);
    }
}

export default new AttendanceModel();
