import './UserRow.css'
import { formatPhone } from '../../utils/phoneUtils';
import eye from '../../assets/pictos/eye.svg'
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

export default function UserRow({user, index}){
    const navigate = useNavigate()

    function goToClientAppointments(path, userId){
        navigate(path)
        setTimeout(()=> {
            document.getElementById(userId).scrollIntoView({behavior:'smooth'})
        }, 100)
    }

    useEffect(()=> {
        console.log('user= ', user)
    }, [user])

    return(
        <tr>
            <td>{index+1}</td>
            <td>{user.fullname}</td>
            <td>{formatPhone(user.phoneNumber)}</td>
            <td>
                <a 
                    href={`mailto:${user.email}`} 
                    target='_blank'
                >
                    {user.email}
                </a>
            </td>
            <td>
                <div>
                    <button
                        className='eye-button'
                        onClick={(e) => goToClientAppointments('/appointments', user.id)}
                    >
                        <img src={eye} alt="to see user's appointments" />
                    </button>
                </div>
            </td>
        </tr>
    );
}