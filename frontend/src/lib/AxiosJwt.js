import axios from 'axios';
import LoginModel from '../model/LoginModel';

const axiosJwt = () => {
  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
  };

  // Create instance
  let instance = axios.create(defaultOptions);

  // Set the AUTH token for any request
  instance.interceptors.request.use(function (config) {
    const token = LoginModel.retrieveToken();
    config.headers.Authorization =  token ? `Bearer ${token}` : '';
    return config;
  });

  instance.interceptors.response.use(function (response) {
    // if(typeof response.data.code !== 'undefined'){
    //   if(response.data.code == '999'){
    //     LoginModel.destory().then(response => {
    //       window.location.replace('/login?sesion=expired');
    //     })
    //     return;
    //   }
    // }else{
    //   return response;
    // }
    return response;

  }, function (error) {
    if (error.response.status == 401) {
      LoginModel.destoryAll().then(res => {
        window.location.replace('/login?session=expired');
      })
    } else {
      return Promise.reject(error);
    }
  });

  return instance;
};

export default axiosJwt();
