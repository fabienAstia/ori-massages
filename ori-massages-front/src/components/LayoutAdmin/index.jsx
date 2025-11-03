import './LayoutAdmin.css'
import HeaderAdmin from '../HeaderAdmin'
import { Outlet } from 'react-router-dom';

export default function LayoutAdmin(){
    return (
        <>
            <HeaderAdmin/>
            <main className='layout-admin-style'>
                <Outlet/>
            </main> 
        </>
    );
}