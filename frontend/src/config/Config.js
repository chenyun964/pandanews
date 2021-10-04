const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/authenticate';
const register_api = API_HOSTS + '/register';
const news_list_api = API_HOSTS + '/news/list'

export default{
    API_HOSTS,
    authenticate_api,
    register_api,
    news_list_api
}
