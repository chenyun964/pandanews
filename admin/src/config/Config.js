const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/admin/authenticate';
const register_api = API_HOSTS + '/admin/register';

//Ogranisation
const org_api = API_HOSTS + '/organisation';
const org_approve_api = API_HOSTS + '/organisation/approve';

export default {
    API_HOSTS,
    authenticate_api,
    register_api,
    org_api,
    org_approve_api
}
