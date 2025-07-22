function Navbar() {
    return (
        <>
            <nav className="navbar navbar-light bg-purple text-white justify-content-between">
                <div className="d-flex px-5 my-1 align-items-center">
                    <h2 className=" mb-0 mr-4 ">Magic</h2>
                    <ul className="nav">
                        <li className="nav-item ">
                            <a className="nav-link text-white link-size" href="/">Home</a>
                        </li>
                        <li className="nav-item ">
                            <a className="nav-link text-white link-size" href="/">My Collection</a>
                        </li>
                    </ul>

                </div>
                <div className="d-flex align-items-center">
                    <p className="mb-0 mx-4" >Username</p>
                    <p className="mb-0 mx-4">Logout</p>
                </div>
            </nav>
        </>
    )
}

export default Navbar;