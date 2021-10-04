const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/authenticate';
const register_api = API_HOSTS + '/register';
const news_api = API_HOSTS + '/news'

export default{
    API_HOSTS,
    authenticate_api,
    register_api,
    news_api
}
