import axios from 'axios'

const USER_API_URL = 'http://localhost:8080/api/v1'

class AuthService {

    login(email, password) {
        return axios.post(`${USER_API_URL}/login`, { email, password })
            .then(response => response.data)
            .catch(error => {
                throw error;
            });
    }
}

export default new AuthService()