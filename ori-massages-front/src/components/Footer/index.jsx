import './Footer.css'
import { Link } from 'react-router-dom';
import phoneNumber from '../../assets/pictos/phone.svg'
import mail from '../../assets/pictos/mail.svg'

export default function Footer(){
    function scrollToTop(){
        setTimeout(() => {
            window.scrollTo(0, 0);
        }, 50)
    }
    return (
        <footer className='footerWrapper nav nabar py-3'>
            <div className='d-flex container justify-content-center align-items-center gap-2'>
                <span className='footerElement'>© 2025 Portfolio</span>
                <Link to={'/'} onClick={scrollToTop} className='nav-link pointer text-center footerElement'>Accueil</Link>
                <Link to={'/legalNotices'} onClick={scrollToTop} className='nav-link pointer text-center footerElement'>Mentions légales</Link>
                <Link to={'privacyPolicy'} onClick={scrollToTop} className='nav-link pointer text-center footerElement'>Politique de confidentialité</Link>
            </div>
            <div className='d-flex container justify-content-center align-items-center gap-4'>
                 <span className="footerElement d-flex align-items-center gap-2">
                    <img src={phoneNumber} alt="Téléphone" />
                    <span>: 01 23 45 67 89</span>
                </span>

                <span className="footerElement d-flex align-items-center gap-2">
                    <img src={mail} alt="Email" />
                    <span>: any@thing.com</span>
                </span>
            </div>
        </footer>
    );
}