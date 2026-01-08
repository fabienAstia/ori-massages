import { useEffect, useState } from 'react'
import './LocationEditModal.css'
import Modal from 'react-bootstrap/Modal'
import Form from 'react-bootstrap/Form'
import { Button } from 'react-bootstrap'
import Address from '../../AddressForm'
import axios from 'axios'
const apiUrl = import.meta.env.VITE_API_URL

export default function LocationEditModal(props){
    function emptyAddress() {
        return {
            streetNumber:'',
            streetName:'',
            complement:'',
            zipCode:'',
            city:''
        }
    }
    const [name, setName] = useState('')
    const [address, setAddress] = useState(emptyAddress())
    const [imagePath, setImagePath] = useState('')
    const [file, setFile] = useState(null)
    const [preview, setPreview] = useState(null)

    function normalizeAddress(addr){
        if(!addr) return emptyAddress()
        return {
            streetNumber: addr.streetNumber,
            streetName: addr.streetName,
            complement: addr.complement ?? '',
            zipCode: addr.zipCode,
            city: addr.city
        }
    }

    useEffect(()=> {
        const location = props.location
        
        setFile(null)
        setPreview(null)

        if(!location){
            setName('')
            setImagePath('')
            setAddress(emptyAddress())
            return;
        }

        const locAddress = location.address

        setName(location.name)
        setImagePath(location.imagePath)
        setAddress(normalizeAddress(locAddress))

    }, [props.location])

    useEffect(()=> {
        if(!file){
            setPreview(`${apiUrl}/uploads/locations/${imagePath}`)
            return;
        }
        setPreview(URL.createObjectURL(file))
    }, [imagePath, file])

    async function submit(){
        try {
            const resp = props.location 
            ? await axios.postForm(`${apiUrl}/locations/${props.location.id}`, 
                {name:name, atHome:props.location.atHome , address: JSON.stringify(address), image:file})
            : await axios.postForm(`${apiUrl}/locations`, 
                {name:name, atHome:false, address: JSON.stringify(address), image:file})
            
            alert('new location !')
            props.setHasBeenModified(true)
            props.onHide()
        }catch(err){
            if(err.response) return console.log(err.response.data)
            if(err.request) return console.log(err.request)
            return console.log(err.message)
        }
    }

    return (
       <Modal show={props.show} onHide={props.onHide} size='lg'>
            <Modal.Header closeButton>
            <Modal.Title>
                {props.location? 'Modifier un Lieu' : 'Ajouter un Lieu'}
            </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                
                <Form>
                    <Form.Group className="mb-3" controlId="locationName">
                        <Form.Label>Nom du Lieu <span className='text-danger'>*</span></Form.Label>
                        <Form.Control 
                            type="text" 
                            required
                            value={name}
                            name='name'
                            onChange={(e) =>  setName(e.target.value)}
                        />
                    </Form.Group>

                    {!props.location?.atHome &&
                        <Address 
                            address={address}
                            setAddress={setAddress}
                        />
                    }

                    <Form.Group className="mb-3" controlId="locationImage">
                        <Form.Label>Image du Lieu
                            {!props.location && 
                                <span className='text-danger'>*</span>
                            }
                        </Form.Label>
                        {preview &&
                            <img 
                                className='format-image'
                                src={preview}
                            />
                        }
                        <Form.Control 
                            type="file"
                            accept=".png, .jpeg, .jpg" 
                            required={!props.location}
                            name='image'
                            onChange={(e) => setFile(e.target.files[0])}    
                        />
                        <Form.Text className="text-muted">
                           ℹ️ Fichiers <span className='text-danger'>.jpeg, .jpg ou .png</span> acceptés.
                        </Form.Text>
                    </Form.Group>
                </Form>

            </Modal.Body>
            <Modal.Footer>
                <Button 
                    variant="primary" 
                    disabled={name == ''}
                    onClick={submit}
                >
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    )
}