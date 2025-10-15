import './BookModal.css';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Modal from 'react-bootstrap/Modal';
import Row from 'react-bootstrap/Row';
import BookingCalendar from '../BookingCalendar';
import { useState } from 'react';

export default function BookModal(props) {
  const [showHours, setShowHours] = useState(false);
  const [hours, setHours] = useState(null);
  console.log('hours', hours)
  return (
    <Modal {...props} 
    aria-labelledby="contained-modal-title-vcenter"
    size='lg'
    onExit={()=>setShowHours(false)}
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
              <Button onClick={() => setHours('10h - 10h45')} className={`'btn-toggle'`}>10h . 10h45</Button>
              <Button onClick={() => setHours('11h - 11h45')} className='btn-toggle'>11h . 11h45</Button>
              <Button onClick={() => setHours('11h45 - 12h30')} className='btn-toggle'>11h45 . 12h30</Button>
              <Button onClick={() => setHours('14h . 14h45')} className='btn-toggle'>14h . 14h45</Button>
              <Button onClick={() => setHours('14h45 . 15h30')} className='btn-toggle'>14h45 . 15h30</Button>
            </Col>
          </Row>
        </Container>
      </Modal.Body>

      <Modal.Footer>
        {/* <Button>Réserver</Button> */}
        <Button onClick={props.onHide} className='btn '>Fermer</Button>
      </Modal.Footer>
    </Modal>
  );
}
