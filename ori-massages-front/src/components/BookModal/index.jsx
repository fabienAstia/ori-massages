import './BookModal.css';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Modal from 'react-bootstrap/Modal';
import Row from 'react-bootstrap/Row';
import HomeCard from '../HomeCard';
import Contact from '../../views/Contact';
import BookingCalendar from '../BookingCalendar';
import { useState } from 'react';
import lieu1 from '../../assets/photos/lieu1.jpg'
import lieu2 from '../../assets/photos/lieu2.jpg'

export default function BookModal(props) {
  const [showHours, setShowHours] = useState(false);
  const [hours, setHours] = useState(null);
  const [showLocations, setShowLocations] = useState(false);
  const [activeSlot, setActiveSlot] = useState(null);
  const [location, setLocation] = useState(null);

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
              onClick={() => setHoursAndUnlockLocations(slot)} 
              className={`btn-toggle ${activeSlot == slot ? 'active' : ''}`}
              >
                {slot}
              </Button>
   })
  const places = [
    {
      id:1,
      image: lieu1,
      title:'210 rue de Belleville'
    }, 
    {
      id:2,
      image: lieu2,
      title: 'à domicile'
    }
  ];
  const placeList = places.map(place => {
   return  <HomeCard
            key={place.id}
            image={place.image}
            title={place.title}
            onClick={() => {
              if(location == place.title) {
                setLocation(null)
              } else {
                setLocation(place.title)
              }
            }}
            className={location == place.title ? 'selected' : '' }
            />
  })

  return (
    <Modal {...props} 
    aria-labelledby="contained-modal-title-vcenter"
    size='lg'
    onExit={()=>{setShowHours(false); 
      setShowLocations(false); 
      setActiveSlot(null); 
      setLocation(null)}}
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {props.title}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body className="grid-example">
        <Container>

          <Row className='d-flex justify-content-center '>
            <Col xs={12} lg={6} className='text-center'>
              <div className='d-flex justify-content-center '>
                <img src={props.image} id='imageModal'/>
              </div>
               <p className='my-2'>{props.description}</p>
               <div className='d-flex justify-content-evenly fs-5 fw-bold'>
                      {props.duration} - {props.price}
               </div>
            </Col>
            <Col xs={12} lg={6} className='d-flex justify-content-center align-items-center'>
                <BookingCalendar />
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
                variant='services'
                />
              </Row>
            </div>
            }
            
        </Container>
      </Modal.Body>

      <Modal.Footer>
        {/* <Button>Réserver</Button> */}
        <Button onClick={props.onHide} className='btn '>Fermer</Button>
      </Modal.Footer>
    </Modal>
  );
}
