const API_HOSTS = process.env.REACT_APP_API_URL;

// Authentication 
const authenticate_api = API_HOSTS + '/admin/authenticate';
const register_api = API_HOSTS + '/admin/register';

// User
const user_profile_api = API_HOSTS + '/users/profile';

// Ogranisation
const org_api = API_HOSTS + '/organisation';
const org_approve_api = API_HOSTS + '/organisation/approval';

// News
const news_api = API_HOSTS + '/news';
const news_generate_bing_api = API_HOSTS + '/news/api';

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

// Measurements
const measurement_api = API_HOSTS + '/measurements';

// Statistics
const statistics_api = API_HOSTS + '/statistic';

// Image upload
const image_api = API_HOSTS + '/image';

// Category
const category_api = API_HOSTS + '/category';


export default {
    news_api,
    news_generate_bing_api,
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
    statistics_api,
    user_profile_api,
    image_api,
    category_api
}
