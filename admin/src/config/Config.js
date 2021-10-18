const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/admin/authenticate';
const register_api = API_HOSTS + '/admin/register';

//Ogranisation
const org_api = API_HOSTS + '/organisation';
const org_approve_api = API_HOSTS + '/organisation/approve';

const news_list_api = '/news/list';

// Vaccination Spots
const vaccispots_api = API_HOSTS + '/vaccispots';
const vaccispots_name_api = vaccispots_api + '/name';
const vaccispots_region_api = vaccispots_api + '/region';
const vaccispots_type_api = vaccispots_api + '/type';
const vaccispots_vaccitype_api = vaccispots_api + '/vaccitype';

export default {
    news_list_api,
    API_HOSTS,
    authenticate_api,
    register_api,
    org_api,
    org_approve_api,
    vaccispots_api,
    vaccispots_name_api,
    vaccispots_region_api,
    vaccispots_type_api,
    vaccispots_vaccitype_api,
}
