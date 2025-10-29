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
  const apiUrl = import.meta.env.VITE_API_URL
  const [date, setDate] = useState(null);
  const [showHours, setShowHours] = useState(false);
  const [slots, setSlots] = useState(null);
  const [showLocations, setShowLocations] = useState(false);
  const [activeSlot, setActiveSlot] = useState(null);
  const [locations, setLocations] = useState(null);
  const [location, setLocation] = useState(null);
  const [contactData, setContactData] = useState()

  function setSlotsAndUnlockLocations(slot){
    if(activeSlot == slot){
      setActiveSlot(null)
      setShowLocations(false)
      setLocation(null)
    } else {
      slot.date = date;
      setActiveSlot(slot);
      setShowLocations(true);
    }
  }

  async function getSlots(date) {
    try {
      console.log('DATE= ', date)
      if(date){
          const response = await axios.post(`${apiUrl}/slots/availables`,{
          date: date,
          prestation: props.prestation
        })
        setSlots(response.data)
        console.log('getSlots=', response.data)
        return response.data;
      }   
    } catch(error){
      if(error.response){
        alert('Failed to getSlots', error.response.data)
      }else if(error.request){
        alert('Failed to execute getSlots(), error.request=', error.request)
      }
    }
  }

  function handleDateChange(data){
    setDate(data);
    getSlots(data);
  }

  const slotList = slots?.map((slot, id) => {
      return <Button 
                key={id}
                onClick={() => {
                  setSlotsAndUnlockLocations(slot)
                  console.log('slot=', slot)
                }} 
                className={`btn-toggle ${activeSlot == slot ? 'active' : ''}`}
              >
                {slot.beginAt} - {slot.endVisible}
              </Button>
   })

  useEffect(() => {
    async function getLocations(){
      try {
        const response = await axios.get(`${apiUrl}/locations`)
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
                console.log('location_id= ', place.id)
              }}
              className={location == place ? 'selected' : '' }
            />
  })

 
  async function handleContactChange(data){
    setContactData(data);
    await submit(data);
  }

  async function submit(data){
      try {
          const res = await axios.post(`${apiUrl}/appointment`, {
              comment: '',
              slot:activeSlot,              
              location:location,
              user:data,
          });
          alert('You\'ve created an appointment')
          props.onHide();
      }catch(err){
          if(err.response){
              console.error('POST FAILED with status= ' + err.response.status)
              console.error('POST FAILED with message= ' + err.response.message)
              console.error('POST FAILED with data= ' + err.response.data)

              console.error('POST FAILED with field= ' + err.response.data.fieldsErrors)
              console.error('POST FAILED with global= ' + err.response.data.globalErrors)
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
      onExit={()=>{
        setDate(null);
        setShowHours(false); 
        setActiveSlot(null); 
        setShowLocations(false); 
        setLocation(null);
      }}>
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
                  onChangeDate={handleDateChange}
                />
            </Col>
          </Row>

          {date &&
            <>
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
                <Button onClick={() => {setShowLocations(activeSlot != null)}} className={`w-100 ${showLocations ? 'bg-custom' : 'bg-none'}`}>Choisissez un lieu</Button>
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
                    bookModalSubmit={(data) => handleContactChange(data)}
                    isAtHome={location.id == 1}
                  />
                </Row>
              </div>
              }
          </>
          }
            
        </Container>
      </Modal.Body>

      <Modal.Footer>
        <Button onClick={props.onHide} className='btn '>Fermer</Button>
      </Modal.Footer>
    </Modal>
  );
}
