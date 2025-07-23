import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'
import AuthService from '../AuthService';
import parseJwt from '../utils/jwtUtils';
import { useAuth } from '../AuthContext';

const LOGIN_FORM = {
    email: '',
    password: ''
}

function Login() {
    const [loginForm, setLoginForm] = useState(LOGIN_FORM);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();
    const { login } = useAuth(); 

    useEffect(() => {
            const token = localStorage.getItem('token');
            if (token) {
                navigate('/home');
            }
    }, []);
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const resp = await AuthService.login(loginForm);
            console.log("Login resp:", resp);
            if (resp.ok) {
                const data = await resp.json();
                await login(data.authenticationToken)
                navigate('/home');
            } else {
                setMessage('Login failed. Please check your credentials.');
            }
        } catch(error) {
            setMessage('')
        } 
    }

    const handleChange = (event) => {
        const newLoginForm = { ...loginForm };
        newLoginForm[event.target.name] = event.target.value;
        setLoginForm(newLoginForm);
    }

    return (
        <>
            <div className="row">
                <div className="col-7 col-md-3 mx-auto mt-5">
                    <div className="card">
                        <h3 className="card-title mx-3 mt-2">Login</h3>
                        <div className="card-body">
                            <form onSubmit={handleSubmit}>
                                <div className="mb-3">
                                    <label htmlFor="email" className="form-label ">Email address</label>
                                    <input type="email" className="form-control form-control-lg" id="email" name="email"
                                    value={loginForm.email} onChange={handleChange} placeholder="name@example.com" />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="password" className="form-label">Password</label>
                                    <input type="password" className="form-control form-control-lg" id="password" name="password"
                                    value={loginForm.password} onChange={handleChange} placeholder="***********" />
                                </div>

                                <div className="col-10 col-md-8 mx-auto alignt-items-center text-center">
                                    <button type="submit" className="btn btn-lg bg-blue text-white px-5 ">Login</button>
                                </div>

                            </form>


                        </div>
                    </div>
                    <p className="my-1 h4">Don't have an account? <Link to={'/register'}>Click here to Register</Link></p>
                </div>
            </div>
        </>
    )
}

export default Login;