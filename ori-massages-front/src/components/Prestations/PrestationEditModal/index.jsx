import './PrestationEditModal.css'
import { useEffect, useState } from 'react'
import Modal from 'react-bootstrap/Modal'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import InputGroup from 'react-bootstrap/InputGroup'
import axios from 'axios'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
const apiUrl = import.meta.env.VITE_API_URL
const EMPTY_FORM = {
    id:-1,
    typeId:'',
    durationId:'',
    name:'',
    description:'',
    price:'',
    displayOrder:'',
    active:false
}

export default function PrestationEditModal(props){
    const [types, setTypes] = useState([])
    const [durations, setDurations] = useState([])
    const [form, setForm] = useState(EMPTY_FORM)
    const [imagePath, setImagePath] = useState('')
    const [charCount, setCharCount] = useState(0);
    const MAX_CHARS = 300;
    const [file, setFile] = useState(null)
    const [preview, setPreview ] = useState(null)

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
        console.log(props.prestation)
        if(props.prestation){
            setForm({
                id: props.prestation.id,
                typeId: props.prestation.typeId,
                durationId: props.prestation.durationId,
                name: props.prestation.name,
                description: props.prestation.description,
                price: props.prestation.price,
                displayOrder: props.prestation.displayOrder,
                active: props.prestation.active
            })
            setImagePath(props.prestation.imagePath)
            setFile(null)
            setCharCount(props.prestation.description.length)
        }else{
            setForm(EMPTY_FORM)
            setImagePath('')
            setFile(null)
            setCharCount(0)
        }
    }, [props.prestation])

    const durationsLabel = durations?.map((duration, i) =>
        <option key={duration.id} value={duration.id} >
            {duration.label}
        </option>
    )

    const typesNames = types?.map((type, i) =>
        <option key={type.id} value={type.id} >
            {type.name}
        </option>
    )

    useEffect(()=> {
        if(file){
            setPreview(URL.createObjectURL(file))
        } else if (imagePath) {
            setPreview(`${apiUrl}/uploads/prestations/${imagePath}`)
        } else {
            setPreview(null)
        }
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
            props.setHasBeenModified(!props.hasBeenModified)
            props.onHide()
        }catch(err){
            if(err.response) return console.log(err.response.data)
            if(err.request) return console.log(err.request)
            return console.log(err.message)
        }
    }

    return (
        <Modal 
            show={props.show} 
            onHide={props.onHide} 
            onExit={()=>{
                setForm(EMPTY_FORM)
                setFile(null)
                setImagePath('')
                setPreview(null)
                setCharCount(0)
            }}
            size='lg'
        >
            <Modal.Header closeButton>
            <Modal.Title>
                {props.prestation
                ? 'Modifier la Prestation' 
                : 'Ajouter une Prestation'}
            </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Container>

                    <Form>
                        <Row>
                            <Col>
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
                            </Col>
                        </Row>
                    
                        <Row>
                            <Col>
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
                            </Col>
                        </Row>
                        
                        <Row>
                            <Col>
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
                            </Col>
                        </Row>

                        <Row>
                            <Col>
                                <Form.Group className="mb-3" controlId='type'>
                                    <Form.Label>
                                        Type de Prestation<span className='text-danger'>*</span>
                                    </Form.Label>
                                    <Form.Select 
                                        name='type'
                                        required
                                        aria-label='type' 
                                        value={form.typeId}
                                        onChange={(e)=> setForm({...form, typeId: Number(e.target.value)})}
                                    >
                                        <option value='' disabled>Choisissez un Type</option>
                                        {typesNames}
                                    </Form.Select>
                                    <Form.Text className="text-muted">
                                        Type de Prestation : Massage, Soin visage, ... 
                                    </Form.Text>
                                </Form.Group>
                            </Col>

                            <Col>
                                <Form.Group className="mb-3" controlId='duration'>
                                    <Form.Label>Durée de la Prestation
                                        <span className='text-danger'>*</span>
                                    </Form.Label>
                                    <Form.Select 
                                        name='duration'
                                        required
                                        aria-label='duration' 
                                        value={form.durationId}
                                        onChange={(e)=> setForm({...form, durationId: Number(e.target.value)})}
                                    >
                                        <option value='' disabled>Choisissez une Durée</option>
                                        {durationsLabel}
                                    </Form.Select>
                                </Form.Group>
                            </Col>
                        </Row>

                        <Row>
                            <Col>
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
                                            onChange={(e)=> setForm({...form, price: Number(e.target.value)})}
                                        />
                                        <InputGroup.Text>€</InputGroup.Text>
                                    </InputGroup>
                                </Form.Group>
                            </Col>
                            
                            <Col>
                                <Form.Group className="mb-3" controlId='display_order'>
                                    <Form.Label>Ordre d'affichage
                                        <span className='text-danger'>*</span>
                                    </Form.Label>
                                    <Form.Control 
                                        type='number'
                                        required
                                        min='1'
                                        name='display_order'
                                        aria-label='display order'
                                        value={form.displayOrder}
                                        onChange={(e)=> setForm({...form, displayOrder: Number(e.target.value)})}
                                    />
                                </Form.Group>
                            </Col>
                            
                            <Col sm={12} md={4}>
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
                                        ℹ️ Si non Active, la prestation ne sera pas proposée aux Utilisateurs
                                    </Form.Text>
                                </Form.Group>
                            </Col>
                        </Row>

                    </Form>

                </Container>
                
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