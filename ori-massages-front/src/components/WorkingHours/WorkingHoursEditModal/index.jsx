import './WorkingHoursEditModal.css'
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form'
import axios from 'axios';
const apiUrl = import.meta.env.VITE_API_URL

export default function WorkingHoursEditModal(props){
    const [startTime, setStartTime] = useState('')
    const [endTime, setEndTime] = useState('')
    const [name, setName] = useState('')

    useEffect(()=> {
        if(props.workingHour){
            setStartTime(props.workingHour.startTime)
            setEndTime(props.workingHour.endTime)
            setName(props.workingHour.name)
        }else{
            setStartTime('')
            setEndTime('')
            setName('')
        }
    }, [props.workingHour])

    async function submit(){
        console.log('startTime', startTime)

        try{
            const resp = (props.workingHour == null) 
            ?   await axios.post(`${apiUrl}/workingHours`,
                {startTime:startTime, endTime:endTime, name:name})
            :   await axios.post(`${apiUrl}/workingHours/${props.workingHour.id}`,
                {startTime:startTime, endTime:endTime, name:name})

            props.setModifiedWorkingHour(resp.data)
            props.onHide()
        }catch(err){
            if(err.response) console.log(err.response)
            if(err.request) console.log(err.request)
        }
    }

    return (
        <Modal show={props.show} onHide={props.onHide} size='lg'>
            <Modal.Header closeButton>
            <Modal.Title>
                {props.workingHour
                ? 'Modifier le créneau horaire' 
                : 'Ajouter un créneau horaire'}
            </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                
                <Form>
                    <Form.Group className="mb-3" controlId="startTime">
                        <Form.Label>Heure du début <span className='text-danger'>*</span></Form.Label>
                        <Form.Control 
                            type="time" 
                            required
                            value={startTime}
                            onChange={(e) => setStartTime(e.target.value)}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="endTime">
                        <Form.Label>Heure de fin <span className='text-danger'>*</span></Form.Label>
                        <Form.Control 
                            type="time" 
                            required
                            value={endTime}
                            onChange={(e) => setEndTime(e.target.value)}    
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="name">
                        <Form.Label>Nom</Form.Label>
                        <Form.Control 
                            type="text" 
                            value={name}
                            onChange={(e) => setName(e.target.value)}   
                        />
                    </Form.Group>
                </Form>

            </Modal.Body>
            <Modal.Footer>
                <Button 
                    variant="primary" 
                    disabled={ startTime == '' || endTime == ''}
                    onClick={submit}
                >
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    )
}