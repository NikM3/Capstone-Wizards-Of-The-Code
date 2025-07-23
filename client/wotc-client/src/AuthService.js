const BASE_URL = "http://localhost:8080/login"
class AuthService {
    login(credentials) {
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials),
        }
        return fetch(BASE_URL, init);
    }
}
export default new AuthService();