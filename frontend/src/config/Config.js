const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/authenticate';
const register_api = API_HOSTS + '/register';

//Ogranisation
const org_create_api = API_HOSTS + '/organisation';
const org_join_api = API_HOSTS + '/organisation/join';
const org_my_org_api = API_HOSTS + "/organisation/owner";

//Measurements
const measurement_api = API_HOSTS + '/measurements';

export default {
    API_HOSTS,
    authenticate_api,
    register_api,
    org_create_api,
    org_join_api,
    org_my_org_api,
    measurement_api
}
