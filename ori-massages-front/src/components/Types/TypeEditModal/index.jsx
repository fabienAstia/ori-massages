import './TypeEditModal.css'
import { useState, useEffect } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form'
import axios from 'axios';
const apiUrl = import.meta.env.VITE_API_URL

export default function TypeEditModal(props){
    const [name, setName] = useState('')
    const [description, setDescription] = useState('')

    useEffect(()=> {
        if(props.type){
            setName(props.type.name)
            setDescription(props.type.description)
        }else{
            setName('')
            setDescription('')
        }
    }, [props.type])

    async function submit(){
        try {
            const resp = (props.type == null)
            ?   await axios.post(`${apiUrl}/types`,
                {name: name, description: description})
            :   await axios.post(`${apiUrl}/types/${props.type.id}`, 
                {name: name, description: description})
            
            props.setModifiedType(resp.data)
            props.onHide()
        } catch(err){
            if(err.response) console.log(err.response)
            if(err.request) console.log(err.request)
        }
    }
    
    return (
        <Modal show={props.show} onHide={props.onHide} size='lg'>
            <Modal.Header closeButton>
            <Modal.Title>
                {props.type
                ? 'Modifier le Type de prestation' 
                : 'Ajouter un Type'}
            </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                
                <Form>
                    <Form.Group className="mb-3" controlId="formBasicName">
                        <Form.Label>Nom du Type <span className='text-danger'>*</span></Form.Label>
                        <Form.Control 
                            type="text" 
                            required
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                        <Form.Text className="text-muted">
                            Type de Prestation : Massage, Soin visage, ... 
                        </Form.Text>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Description <span className='text-danger'>*</span></Form.Label>
                        <Form.Control 
                            as="textarea" 
                            rows={3}
                            required
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}    
                        />
                    </Form.Group>
                </Form>

            </Modal.Body>
            <Modal.Footer>
                <Button 
                    variant="primary" 
                    disabled={name == '' || description == ''}
                    onClick={submit}
                >
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    );
}
