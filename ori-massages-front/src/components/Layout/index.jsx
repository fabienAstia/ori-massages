import './Layout.css'
import { Outlet, useLocation } from "react-router-dom";
import Header from '../Header'
import Footer from '../Footer';

export default function Layout(){
    const location = useLocation();
    const isHome = location.pathname === '/';

    return (
        <div className='layout-container'>
            <Header/>
            <main className={isHome ? '' : "layoutStyle"}>
                <Outlet/>
            </main> 
            <Footer/>
        </div>
    );
}