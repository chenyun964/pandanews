import axios from 'axios';
import config from '../config/Config';
import Cookies from 'universal-cookie';

const cookies = new Cookies();
const maxage = 2592000;
class LoginModel {
  async register(data) {
    return axios.post(config['register_api'], data);
  }

  async authenticate(data) {
    return axios.post(config['authenticate_api'], data);
  }

  storeTokens(data) {
    cookies.set("_pandanewsadminot", data.token, { maxAge: maxage });
  }

  async destoryAll() {
    cookies.remove('_pandanewsadminot', { path: '/' });
  }

  retrieveToken() {
    return cookies.get("_pandanewsadminot");
  }
}

export default new LoginModel();
