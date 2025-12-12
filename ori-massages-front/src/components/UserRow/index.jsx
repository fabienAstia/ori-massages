import './UserRow.css'
import { formatPhone } from '../../utils/phoneUtils';

export default function UserRow({user, index}){
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
        </tr>
    );
}