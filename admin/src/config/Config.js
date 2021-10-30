const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/admin/authenticate';
const register_api = API_HOSTS + '/admin/register';

//Ogranisation
const org_api = API_HOSTS + '/organisation';
const org_approve_api = API_HOSTS + '/organisation/approve';

//News
const news_list_api = '/news/list';
const news_find_api = '/news/find/id';
const news_update_api = '/news/update';
const news_create_api = '/news/create';
const news_delete_api = '/news/delete';
const news_generate_bing_api = '/news/api';

// Vaccination Spots
const vaccispots_api = API_HOSTS + '/vaccispots';
const vaccispots_name_api = vaccispots_api + '/name';
const vaccispots_region_api = vaccispots_api + '/region';
const vaccispots_type_api = vaccispots_api + '/type';
const vaccispots_vaccitype_api = vaccispots_api + '/vaccitype';

// Test Spots
const testspots_api = API_HOSTS + '/testspots';
const testspots_name_api = vaccispots_api + '/name';
const testspots_type_api = vaccispots_api + '/type';

//Measurements
const measurement_api = API_HOSTS + '/measurements';
const mea_create_api = API_HOSTS + '/measurements';


export default {
    news_find_api,
    news_update_api,
    news_create_api,
    news_delete_api,
    news_generate_bing_api,
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
    testspots_api,
    testspots_name_api,
    testspots_type_api,
    measurement_api,
    mea_create_api
}
