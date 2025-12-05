import './ManagePrestations.css'
import { Table } from 'react-bootstrap'

export default function ManagePrestations(){
    return (
        <div className='manage-prestations'>
            <h1>Gérer les prestations</h1>

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
                {}
            </tbody>
            </Table>
        </div>
    )
}