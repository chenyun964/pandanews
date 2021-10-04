const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/authenticate';
const register_api = API_HOSTS + '/register';

//Ogranisation
const org_create_api = API_HOSTS + '/organisation';
const org_join_api = API_HOSTS + '/organisation/join';
const org_my_org_api = API_HOSTS + "/organisation/owner";

// Vaccination Spots
const vaccispots_api = API_HOSTS + '/vaccispots';
const vaccispots_name_api = vaccispots_api + '/name';
const vaccispots_region_api = vaccispots_api + '/region';
const vaccispots_type_api = vaccispots_api + '/type';
const vaccispots_vaccitype_api = vaccispots_api + '/vaccitype';

export default{
    API_HOSTS,
    authenticate_api,
    register_api,
    org_create_api,
    org_join_api,
    org_my_org_api,
    vaccispots_api,
    vaccispots_name_api,
    vaccispots_region_api,
    vaccispots_type_api,
    vaccispots_vaccitype_api
}
