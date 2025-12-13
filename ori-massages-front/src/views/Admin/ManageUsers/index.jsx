import './ManageUsers.css'
import { useEffect, useState } from 'react';
import UserRow from '../../../components/UserRow'
import Table from 'react-bootstrap/Table'
import axios from 'axios';
const apiUrl = import.meta.env.VITE_API_URL;

export default function ManageUsers(){
    const [users, setSelectedUsers] = useState([])
    const [rows, setSelectedRows] = useState([])

    useEffect(()=>{
        async function getAllUsers(){
            try {
                const resp = await axios.get(`${apiUrl}/users`)
                setSelectedUsers(resp.data);
            } catch(err){
                if(err.response){
                    console.log('error', err.response.data)
                }else if(err.request){
                    console.log(err.request)
                }
            }
        }getAllUsers()
    }, [])

    useEffect(()=> {
        console.log('users', users)
         console.log('rows', rows)
    })

    useEffect(()=> {
        const rows = users?.map((user, i) => 
            <UserRow 
                key={user.id} 
                index={i}
                user={user} 
            />
        )
        setSelectedRows(rows)   
    }, [users])
  

  return (
    <div className='manage-users'>
        <h1>Clients</h1>

        <Table striped bordered hover className='align-middle'>
            <thead className='text-center'>
                <tr>
                <th>#</th>
                <th>Nom complet</th>
                <th>Téléphone</th>
                <th>Email</th>
                <th>Rdv</th>
                </tr>
            </thead>
            <tbody className='text-center'>
                {rows}
            </tbody>
        </Table>
    </div>
  );
}
