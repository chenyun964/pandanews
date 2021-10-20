const API_HOSTS = process.env.REACT_APP_API_URL;

//Authentication 
const authenticate_api = API_HOSTS + '/authenticate';
const register_api = API_HOSTS + '/register';

//News
const news_list_api = API_HOSTS + '/news/list'
const news_get_top_4_api = API_HOSTS + '/news/find/top4news'
const category_news_api = API_HOSTS + '/news/find/category'
const search_news_api = API_HOSTS + '/news/find/keyword'


//Category
const category_list_api = API_HOSTS + '/category/list'

//User
const user_api = API_HOSTS + '/users';
const user_profile_api = API_HOSTS + '/users/profile';
const user_org_api = API_HOSTS + '/users/organisation';
const user_vaccine_api = API_HOSTS + "/users/vaccine";


//Ogranisation
const org_api = API_HOSTS + '/organisation';
const org_join_api = API_HOSTS + '/users/organisation';
const org_my_org_api = API_HOSTS + "/organisation/owner";
const org_employee_api = API_HOSTS + "/organisation/employee";
const org_employee_promote_api = API_HOSTS + "/organisation/promote";
const org_employee_demote_api = API_HOSTS + "/organisation/demote";

//Measurements
const measurement_api = API_HOSTS + '/measurements';
const mea_create_api = API_HOSTS + '/measurements';

// Vaccination Spots
const vaccispots_api = API_HOSTS + '/vaccispots';
const vaccispots_name_api = vaccispots_api + '/name';
const vaccispots_region_api = vaccispots_api + '/region';
const vaccispots_type_api = vaccispots_api + '/type';
const vaccispots_vaccitype_api = vaccispots_api + '/vaccitype';

//Attendance
const mark_attendance_api = API_HOSTS + '/attendance/mark';

export default{
    API_HOSTS,
    search_news_api,
    mark_attendance_api,
    category_news_api,
    news_get_top_4_api,
    authenticate_api,
    register_api,
    org_api,
    org_join_api,
    org_my_org_api,
    category_list_api,
    news_list_api,
    user_org_api,
    user_api,
    user_profile_api,
    org_employee_api,
    measurement_api,
    vaccispots_api,
    vaccispots_name_api,
    vaccispots_region_api,
    vaccispots_type_api,
    vaccispots_vaccitype_api,
    user_vaccine_api,
    org_employee_promote_api,
    org_employee_demote_api,
    mea_create_api
}
