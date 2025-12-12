import './DurationDeleteModal.css'
import Modal from 'react-bootstrap/Modal'
import Button from 'react-bootstrap/Button'
import axios from 'axios'
const apiUrl = import.meta.env.VITE_API_URL

export default function DurationDeleteModal(props){

    async function handleDelete() {
        try{
            await axios.delete(`${apiUrl}/durations/${props.duration.id}`)
            props.setDeleteDuration(props.duration)
            props.onHide()
        }catch (err){
            if (err.response) console.log(err.response)
            if (err.request) console.log(err.request)
        }
    }

    return (
        <Modal show={props.show} onHide={props.onHide}>
            <Modal.Header closeButton>
            <Modal.Title>⚠️ Suppression</Modal.Title>
            </Modal.Header>
            <Modal.Body className='body-modal'>
                    Êtes vous sûr de vouloir supprimer ?
            </Modal.Body>
            <Modal.Footer>
            <Button 
                variant="danger" 
                onClick={handleDelete}
            >
                Oui
            </Button>

            <Button variant="secondary" onClick={props.onHide}>
                Non
            </Button>
            </Modal.Footer>
        </Modal>
    )
}
