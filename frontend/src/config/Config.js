const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/authenticate';
const register_api = API_HOSTS + '/register';

//User
const user_api = API_HOSTS + '/users';
const user_profile_api = API_HOSTS + '/users/profile';
const user_org_api = API_HOSTS + '/users/organisation';

//Ogranisation
const org_api = API_HOSTS + '/organisation';
const org_join_api = API_HOSTS + '/users/organisation';
const org_my_org_api = API_HOSTS + "/organisation/owner";
const org_employee_api = API_HOSTS + "/organisations/employee";

//Measurements
const measurement_api = API_HOSTS + '/measurements';

export default {
    API_HOSTS,
    authenticate_api,
    register_api,
    org_api,
    org_join_api,
    org_my_org_api,
    user_org_api,
    user_api,
    user_profile_api,
    org_employee_api,
    measurement_api
}
