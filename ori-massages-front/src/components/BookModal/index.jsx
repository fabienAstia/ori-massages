import './BookModal.css';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Modal from 'react-bootstrap/Modal';
import Row from 'react-bootstrap/Row';
import HomeCard from '../HomeCard';
import Contact from '../../views/Contact';
import BookingCalendar from '../BookingCalendar';
import { useEffect, useState } from 'react';
import axios from 'axios';

export default function BookModal(props) {
  if(!props.prestation)return null;
  const [date, setDate] = useState(null);
  const [showHours, setShowHours] = useState(false);
  const [hours, setHours] = useState(null);
  const [showLocations, setShowLocations] = useState(false);
  const [activeSlot, setActiveSlot] = useState(null);
  const [locations, setLocations] = useState(null)
  const [location, setLocation] = useState(null);
  const [contactData, setContactData] = useState({
    email:"",
    firstname:"",
    lastname:"",
    phoneNumber:"",
    message:""
  })

  function setHoursAndUnlockLocations(slot){
    if(activeSlot == slot){
      setHours(null)
      setActiveSlot(null)
      setShowLocations(false)
      setLocation(null)
    } else {
      setHours(slot);
      setActiveSlot(slot);
      setShowLocations(true);
    }
  }

  const slots= ['10h - 10h45', '11h - 11h45', '11h45 - 12h30', '14h . 14h45', '14h45 . 15h30'];
  const slotList = slots.map((slot, id) => {
      return <Button 
                key={id}
                onClick={() => {
                  setHoursAndUnlockLocations(slot)
                  console.log('slot', slot)
                }} 
                className={`btn-toggle ${activeSlot == slot ? 'active' : ''}`}
              >
                {slot}
              </Button>
   })

  useEffect(() => {
    async function getLocations(){
      try {
        const response = await axios.get('http://localhost:8080/locations')
        setLocations(response.data);
      } catch(err){
        if(err.response){
          alert(err.response.data)
        }else if(err.resquest){
          alert(err.request)
        }
      }
    }
    getLocations();
  }, [])

  const placeList = locations?.map(place => {
    return <HomeCard
              key={place.id}
              image={`/photos/${place.imagePath}`}
              title={place.name}
              address={place.address}
              onClick={() => {
                if(location == place) {
                  setLocation(null)
                } else {
                  setLocation(place)
                }
                console.log('location_name= ', place.name)
              }}
              className={location == place ? 'selected' : '' }
            />
  })

  function handleContactChange(data){
    setContactData(data);
    submit();
  }

  async function submit(){
      try {

          const res = await axios.post('http://localhost:8080/appointment', {
              prestation: props.prestation,
              date: date,
              hours: hours,
              location:location,
              user:contactData,
              customerAddress:''
          });

      }catch(err){
          if(err.response){
              console.error('POST FAILED with status= ' + err.response.status)
          } else {
              console.error(err)
              alert(err)
          }
      }
  }

  return (
    <Modal {...props} 
      aria-labelledby="contained-modal-title-vcenter"
      size='lg'
      onExit={()=>{setShowHours(false); 
        setActiveSlot(null); 
        setShowLocations(false); 
        setLocation(null)}}
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {props.prestation.name}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body className="grid-example">
        <Container>

          <Row className='d-flex justify-content-center '>
            <Col xs={12} lg={6} className='text-center'>
              <div className='d-flex justify-content-center '>
                <img src={`/photos/${props.prestation.imagePath}`} id='imageModal'/>
              </div>
               <p className='my-2'>{props.prestation.description}</p>
               <div className='d-flex justify-content-evenly fs-5 fw-bold'>
                      {props.prestation.duration.label} - {props.prestation.price + '€'}
               </div>
            </Col>
            <Col xs={12} lg={6} className='d-flex justify-content-center align-items-center'>
                <BookingCalendar 
                  onChangeDate={setDate}
                />
            </Col>
          </Row>

          <Row className='my-2 d-flex'>
            <Col xl={12} className='d-flex justify-content-center w-100'>
              <Button onClick={() => {setShowHours(!showHours)}} className={`w-100 ${showHours ? 'bg-custom' : 'bg-none'}`}>{showHours ? 'Masquer les horaires' : 'Afficher les horaires'}</Button>
            </Col>
          </Row>
          <Row className={`section-hours ${showHours ? 'd-hours' : 'd-none'}`} >
            <Col className='d-flex justify-content-center flex-wrap my-3'>
             {slotList}
            </Col>
          </Row>

          <Row className={`section-locations ${showLocations ? 'd-locations' : 'd-none'}`} >
            <Col xl={12} >
              <Button onClick={() => {setShowLocations(hours != null)}} className={`w-100 ${showLocations ? 'bg-custom' : 'bg-none'}`}>Choisissez un lieu</Button>
            </Col>
          </Row>
          <Row className={`row row-cols-2 justify-content-center my-3 ${showLocations ? 'd-locations' : 'd-none'}`}>
            {placeList}
          </Row>
                 
            {location &&
            <div>
              <Row>
                <Col xl={12} className='d-flex justify-content-center w-100'>
                  <Button className='w-100 bg-custom'>Rentrez vos coordonnées</Button>
                </Col>
              </Row>
              <Row>
                <Contact
                  onSubmit={(data) => handleContactChange(data)}
                />
              </Row>
            </div>
            }
            
        </Container>
      </Modal.Body>

      <Modal.Footer>
        <Button onClick={props.onHide} className='btn '>Fermer</Button>
      </Modal.Footer>
    </Modal>
  );
}
