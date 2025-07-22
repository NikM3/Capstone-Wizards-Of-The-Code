import { Link } from 'react-router-dom'

function Register() {
    return (
        <>
            <div className="row">
                <div className="col-6 col-md-4 mx-auto mt-5">
                    <div class="card">
                        <h3 class="card-title mx-3 mt-4">Register</h3>
                        <div class="card-body">
                            <form>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email address</label>
                                    <input type="email" class="form-control form-control-lg" id="email" placeholder="name@example.com" />
                                </div>
                                <div class="mb-3">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control form-control-lg" id="username" placeholder="johndoe" />
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-control form-control-lg" id="password" placeholder="***********" />
                                </div>
                                <div class="mb-3">
                                    <label for="confirmPassword" class="form-label">Confirm Password</label>
                                    <input type="confirmPassword" class="form-control form-control-lg" id="confirmPassword" placeholder="***********" />
                                </div>
                                <div className="col-10 col-md-8 mx-auto alignt-items-center text-center">
                                    <button type="submit" class="btn btn-lg bg-blue text-white px-5 ">Register</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <p className="my-1 h4">Already have an account? <Link to={'/'} >Click here to Login</Link></p>
                </div>
            </div>
        </>
    )
}

export default Register;