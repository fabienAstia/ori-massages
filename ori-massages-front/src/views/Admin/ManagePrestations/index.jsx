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
                <th>Type</th>
                <th>Durée</th>
                <th>Nom </th>
                <th>Description</th>
                <th>Tarif</th>
                <th>Activée ?</th>
                </tr>
            </thead>
            <tbody className='text-center'>
                {}
            </tbody>
            </Table>
        </div>
    )
}