import './HeaderAdmin.css'
import {Link, NavLink} from 'react-router-dom'
import logo from '../../assets/photos/logo-fond-blanc-espace.svg'
// import adminPicto from '../../assets/pictos/admin.svg'

export default function Header(){
    return (
        <header className="header">
        <nav className="navbar navbar-custom navbar-expand-md sticky-top p-0 style w-100" >
            <div className="container-fluid p-0">
                <Link to="/" className='navbar-brand logo-wrapper'><img src={logo} id='logo'/></Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapseNavBar" aria-controls="collapseNavBar" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse fs-5" id="collapseNavBar">
                    <div className="navbar-nav">
                        <NavLink to="/dates" className='nav-link nav-link-custom'>Dates</NavLink>
                        <NavLink to="/users" className='nav-link nav-link-custom'>Clients</NavLink>
                    </div>
                
                </div>
                {/* <div className="collapse navbar-collapse fs-5 justify-content-end" id="collapseNavBar">
                    <div className='navbar-nav d'>
                        <NavLink to="/admin" className='nav-link nav-link-custom'>
                            <img src={adminPicto}/>
                        </NavLink>
                    </div>
                </div> */}
            </div>
        </nav>
    </header>
    );
}