import './Header.css'
import {Link, NavLink} from 'react-router-dom'
import logo from '../../assets/photos/logo-fond-blanc-espace.svg'

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
                        <NavLink to="/services" className='nav-link nav-link-custom'>Prestations</NavLink>
                        <NavLink to="/about-me" className='nav-link nav-link-custom'>À propos</NavLink>
                        <NavLink to="/solidarity-fund" className='nav-link nav-link-custom'>Cagnotte solidaire</NavLink>
                        <NavLink to="/contact" className='nav-link nav-link-custom'>Contact</NavLink>
                    </div>
                </div>
            </div>
        </nav>
    </header>
    );
}