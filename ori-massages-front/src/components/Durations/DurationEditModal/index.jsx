import './DurationEditModal.css'
import { useState, useEffect } from 'react'
import Modal from 'react-bootstrap/Modal'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import axios from 'axios'
const apiUrl = import.meta.env.VITE_API_URL

export default function DurationEditModal(props){
    const [value, setValue] = useState('')
    const [label, setLabel] = useState('')
    const [breakTime, setBreakTime] = useState('')

    useEffect(()=> {
        if(props.duration){
            setValue(props.duration.value)
            setLabel(props.duration.label)
            setBreakTime(props.duration.breakTime)
        }else{
            setValue('')
            setLabel('')
            setBreakTime('')
        }
    }, [props.duration])

    async function submit(){
        try {
            const resp = (props.duration == null) 
            ?   await axios.post(`${apiUrl}/durations`,
                {value:value, label:label, breakTime:breakTime})
            :   await axios.post(`${apiUrl}/durations/${props.duration.id}`,
                {value:value, label:label, breakTime:breakTime})
            props.setModifiedDuration(resp.data)
            props.onHide()
        }catch (err){
            if(err.response) console.log(err.response)
            if(err.request) console.log(err.request)
        }
    }

    return (
         <Modal show={props.show} onHide={props.onHide} size='lg'>
            <Modal.Header closeButton>
            <Modal.Title>
                {props.duration? 'Modifier la Durée de prestation' : 'Ajouter une Durée'}
            </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                
                <Form>
                    <Form.Group className="mb-3" controlId="formBasicName">
                        <Form.Label>Valeur de la durée <span className='text-danger'>*</span></Form.Label>
                        <Form.Control 
                            type="number" 
                            required
                            value={value}
                            onChange={(e) => setValue(e.target.value)}
                        />
                        <Form.Text className="text-muted">
                            Durée de la Prestation en minutes
                        </Form.Text>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Libellé <span className='text-danger'>*</span></Form.Label>
                        <Form.Control 
                            type="text" 
                            required
                            value={label}
                            onChange={(e) => setLabel(e.target.value)}    
                        />
                        <Form.Text className="text-muted">
                            Ce qui s'affiche à l'écran de l'utilisateur
                        </Form.Text>
                    </Form.Group>

                     <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Temps de pause <span className='text-danger'>*</span></Form.Label>
                        <Form.Control 
                            type="number" 
                            required
                            value={breakTime}
                            onChange={(e) => setBreakTime(e.target.value)}    
                        />
                        <Form.Text className="text-muted">
                           ℹ️ Une pause est ajoutée après chaque prestation.
                            Elle ajuste les créneaux proposés.
                            Mettre 0 pour la désactiver.
                        </Form.Text>
                    </Form.Group>
                </Form>

            </Modal.Body>
            <Modal.Footer>
                <Button 
                    variant="primary" 
                    disabled={value == '' || label == '' || breakTime == ''}
                    onClick={submit}
                >
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    )
}