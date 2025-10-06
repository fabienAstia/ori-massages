import { Outlet, useLocation } from "react-router-dom";
import Header from '../Header'
import './Layout.css'

export default function Layout(){
    const location = useLocation();
    const isHome = location.pathname === '/';
    return (
        <>
            <Header/>
            <main className={isHome ? '' : "layoutStyle"}>
                <Outlet/>
            </main> 
        </>
    );
}