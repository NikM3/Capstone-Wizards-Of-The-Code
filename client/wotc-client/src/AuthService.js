const BASE_URL = "http://localhost:8080"
class AuthService {
    login(credentials) {
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials),
        }
        return fetch(`${BASE_URL}/login`, init);
    }

    register(credentials) {
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials),
        }
        return fetch(`${BASE_URL}/register`, init);
    }
}
export default new AuthService();