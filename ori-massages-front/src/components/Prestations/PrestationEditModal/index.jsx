import './PrestationEditModal.css'
import { useEffect, useState } from 'react'
import Modal from 'react-bootstrap/Modal'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import InputGroup from 'react-bootstrap/InputGroup'
import axios from 'axios'
const apiUrl = import.meta.env.VITE_API_URL

export default function PrestationEditModal(props){
    const [types, setTypes] = useState([])
    const [durations, setDurations] = useState([])
    const [form, setForm] = useState({
        id:-1,
        typeName:'',
        durationLabel:'',
        name:'',
        description:'',
        price:-1,
        active:false
    })
    const [imagePath, setImagePath] = useState('')
    const [charCount, setCharCount] = useState(0);
    const MAX_CHARS = 300;
    const [file, setFile] = useState(null)
    const [preview, setPreview ] = useState(null)

    function validFields(){
        return form.id >= 0 &&
            form.typeName != '' && 
            form.durationLabel != '' &&
            form.name != '' &&
            form.description != ''  &&
            form.price >= 0 
    }

    async function getDurations() {
        try {
            const resp = await axios.get(`${apiUrl}/durations`)
            setDurations(resp.data)
        }catch(err){
            if(err.response) return console.log(err.response.data)
            if(err.request) return console.log(err.request)
            return console.log(err.message)
        }
    }

    async function getTypes() {
        try {
            const resp = await axios.get(`${apiUrl}/types`)
            setTypes(resp.data)
        }catch(err){
            if(err.response) return console.log(err.response.data)
            if(err.request) return console.log(err.request)
            return console.log(err.message)
        }
    }

    useEffect(()=> {
        getDurations(), getTypes()
    }, [])

    useEffect(()=> {
        if(props.prestation){
            setForm({...form, 
                id: props.prestation.id,
                typeName: props.prestation.typeName,
                durationLabel: props.prestation.durationLabel,
                name: props.prestation.name,
                description: props.prestation.description,
                price: props.prestation.price,
                active: props.prestation.active
            })
            setImagePath(props.prestation.imagePath)
            setFile(null)
            setCharCount(props.prestation.description.length)
        }
    }, [props.prestation])

    const durationsLabel = durations?.map((duration, i) =>
        <option key={duration.id} value={duration.label} >
            {duration.label}
        </option>
    )

    const typesNames = types?.map((type, i) =>
        <option key={type.id} value={type.name} >
            {type.name}
        </option>
    )

    useEffect(()=> {
        if(!file){
            return setPreview(`${apiUrl}/uploads/prestations/${imagePath}`)
        }
        setPreview(URL.createObjectURL(file))
    }, [imagePath, file])

    async function submit(){
        if (!file && !imagePath) {
            alert("Une image est obligatoire")
            return
        }
        try {
            const formData = new FormData()
            Object.entries(form).forEach(([key, value])=> {
                formData.append(key, value)
            }) 
            if(file){
                formData.append('image', file)
            }

            const resp = props.prestation 
            ? await axios.post(`${apiUrl}/prestations/${props.prestation.id}`, formData)
            : await axios.post(`${apiUrl}/prestations`, formData)
            
            alert('Prestation enregistrée !')
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
                {props.prestation
                ? 'Modifier la Prestation' 
                : 'Ajouter une Prestation'}
            </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                
                <Form>

                    <Form.Group className="mb-3" controlId='name'>
                        <Form.Label>Nom
                            <span className='text-danger'>*</span>
                        </Form.Label>
                        <Form.Control 
                            type='text'
                            required
                            aria-label='name' 
                            value={form.name} 
                            onChange={(e)=> setForm({...form, name:e.target.value})}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId='type'>
                        <Form.Label>
                            Type de Prestation<span className='text-danger'>*</span>
                        </Form.Label>
                        <Form.Select 
                            name='type'
                            required
                            aria-label='type' 
                            value={typesNames ? form.typeName : ''}
                            onChange={(e)=> setForm({...form, typeName:e.target.value})}
                        >
                            {typesNames}
                        </Form.Select>
                        <Form.Text className="text-muted">
                            Type de Prestation : Massage, Soin visage, ... 
                        </Form.Text>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId='duration'>
                        <Form.Label>Durée de la Prestation
                            <span className='text-danger'>*</span>
                        </Form.Label>
                        <Form.Select 
                            name='duration'
                            required
                            aria-label='duration' 
                            value={durationsLabel ? form.durationLabel : ''}
                            onChange={(e)=> setForm({...form, durationLabel: e.target.value})}
                        >
                            {durationsLabel}
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId='price'>
                        <Form.Label>Prix
                            <span className='text-danger'>*</span>
                        </Form.Label>
                        <InputGroup className="mb-3">
                            <Form.Control 
                                type='number'
                                name='price'
                                required
                                aria-label='price'
                                value={form.price}
                                onChange={(e)=> setForm({...form, price: e.target.value})}
                            />
                            <InputGroup.Text>€</InputGroup.Text>
                        </InputGroup>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId='description'>
                        <Form.Label>Description
                            <span className='text-danger'>*</span>
                        </Form.Label>
                        <Form.Control 
                            as='textarea'
                            rows={3}
                            required
                            maxLength={MAX_CHARS}
                            name='description'
                            aria-label='description'
                            value={form.description}
                            onChange={(e)=> {
                                setForm({...form, description: e.target.value}),
                                setCharCount(e.target.value.length)
                            }}
                        />
                        <div id='remaining-char'>{MAX_CHARS-charCount} caractères restants</div>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId='active'>
                        <Form.Label>Active ?
                            <span className='text-danger'>*</span>
                        </Form.Label>
                        <Form.Check 
                            type='switch'
                            name='active'
                            required
                            aria-label='is active ?'
                            checked={form.active}
                            onChange={(e)=>setForm({...form, active: e.target.checked})
                            }
                        />
                        <Form.Text className='text-muted'>
                            ℹ️ Si elle n'est pas activée, la prestation ne sera pas proposée aux Utilisateurs
                        </Form.Text>
                    </Form.Group>

              
                    <Form.Group className="mb-3" controlId='image'>
                        <Form.Label>Image
                            <span className='text-danger'>*</span>
                        </Form.Label>
                        {preview &&
                            <img 
                                className='format-image'
                                alt='preview'
                                src={preview}
                            />
                        }
                        <Form.Control 
                            type='file'
                            accept=".png, .jpeg, .jpg" 
                            required={!imagePath}
                            aria-label='new image' 
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
                    // disabled={validFields()}
                    onClick={submit}
                >
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    )
}