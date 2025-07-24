import { Link, useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react';
import { useAuth } from '../AuthContext';
import AuthService from '../AuthService';

const REGISTER_FORM = {
    email: '',
    password: '',
    confirmPassword: '',
    username: ''
}

function Register() {
    const [registerForm, setRegisterForm] = useState(REGISTER_FORM);
    const [message, setMessage] = useState('');
    const [errors, setErrors] = useState([]);
    const [loading, setLoading] = useState(false);
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
        if (registerForm.email === "") {
            setMessage('Please enter your email address');
        } else if (registerForm.username === "") {
            setMessage('Please enter your username');
        } else if (registerForm.password === "" || registerForm.confirmPassword === "") {
            setMessage('Please enter your password');
        } else {
            if (registerForm.password !== registerForm.confirmPassword) {
                setMessage('Passwords do not match.');
                console.log("Passwords do not match.");
            } else {
                try {
                    setLoading(true)
                    const resp = await AuthService.register(registerForm);

                    if (resp.ok) {
                        const data = await resp.json();
                        await login(data.authenticationToken)
                        setLoading(false)
                        navigate('/home');
                    } else {
                        setLoading(false)
                        console.log('Register failed. Please check your credentials.')
                        setMessage('Register failed. Please check your credentials.');
                    }
                } catch (error) {
                    setLoading(false)
                    console.log(error)
                    setErrors(error)
                    setMessage('')
                }
            }
        }
    }

    const handleChange = (event) => {
        const newRegisterForm = { ...registerForm };
        newRegisterForm[event.target.name] = event.target.value;
        setRegisterForm(newRegisterForm);
    }



    return (
        <>
            <div className="row">
                <div className="col-7 col-md-3 mx-auto mt-5">
                    <div className="card">
                        <h3 className="card-title mx-3 mt-4">Register</h3>
                        <div className="card-body">
                            <form onSubmit={handleSubmit}>
                                <div className="mb-3">
                                    <label htmlFor="email" className="form-label">Email address</label>
                                    <input type="email" className="form-control form-control-lg" id="email" name="email"
                                        value={registerForm.email} onChange={handleChange} placeholder="name@example.com" />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="username" className="form-label">Username</label>
                                    <input type="text" className="form-control form-control-lg" id="username" name="username"
                                        value={registerForm.username} onChange={handleChange} placeholder="johndoe" />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="password" className="form-label">Password</label>
                                    <input type="password" className="form-control form-control-lg" id="password" name="password"
                                        value={registerForm.password} onChange={handleChange} placeholder="***********" />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="confirmPassword" className="form-label">Confirm Password</label>
                                    <input type="password" className="form-control form-control-lg" name="confirmPassword" id="confirmPassword"
                                        value={registerForm.confirmPassword} onChange={handleChange} placeholder="***********" />
                                </div>
                                <div className="col-10 col-md-8 mx-auto alignt-items-center text-center">
                                    <button type="submit" className="btn btn-lg bg-blue text-white px-5 ">
                                        {loading === true && (
                                            <>
                                                <span class="spinner-border text-white spinner-border-sm" role="status" aria-hidden="true"></span>
                                                <span class="visually-hidden">Loading...</span>
                                            </>
                                        )}
                                        {loading === false && (
                                            <>
                                                Register
                                            </>
                                        )}

                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <p className="my-1 h4">Already have an account? <Link to={'/'} >Click here to Login</Link></p>
                    <div className="container">
                        {message.length > 0 && (
                            <div className="alert alert-danger">
                                <ul>
                                    {message}
                                </ul>
                            </div>
                        )}
                        {errors.length > 0 && (
                            <div className="alert alert-danger">
                                <p>The following errors where found:</p>
                                <ul>
                                    {errors}
                                </ul>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </>
    )
}

export default Register;