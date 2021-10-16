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

  async storeTokens(data) {
    cookies.set("_pandanewsot", data.token, { maxAge: maxage });
  }

  destoryAll() {
    cookies.remove('_pandanewsot', { path: '/' });
  }

  retrieveToken() {
    console.log(cookies.get("_pandanewsot"))
    return cookies.get("_pandanewsot");
  }
}

export default new LoginModel();
