import './CustomMassage.css'
import HomeCard from '../HomeCard'
import ServiceCard from '../ServiceCard'
import BookModal from '../BookModal'
import { useState } from 'react'
import photo1 from '../../assets/photos/photo_massage1.jpg'
import photo2 from '../../assets/photos/photo_massage2.jpeg'
import photo3 from '../../assets/photos/photo_massage3.webp'

export default function CustomMassage({showDescription = true, variant}){
    const [modalShow, setModalShow] = useState(false);
    const [selectedTitle, setSelectedTitle] = useState('');
    const [selectedImage, setSelectedImage] = useState('');
    const [selectedPrice, setSelectedPrice] = useState('');
    const [selectedDuration, setSelectedDuration] = useState(0);
    const [selectedDescription, setSelectedDescription] = useState('');
    
    const listMassage = massages.map(massage =>
        showDescription ?
        <div className='col' key={massage.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <ServiceCard
                title={massage.title}
                image={massage.image}
                duration={massage.duration}
                price={massage.price}
                description={showDescription? massage.description : undefined}
                variant={variant}
                setModalShow={setModalShow}
                setSelectedTitle={setSelectedTitle}
                setSelectedImage={setSelectedImage}
                setSelectedPrice={setSelectedPrice}
                setSelectedDuration={setSelectedDuration}
                setSelectedDescription={setSelectedDescription}
                />
            </div>
        </div>
        :
        <div className='col' key={massage.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <HomeCard
                title={massage.title}
                image={massage.image}
                />
            </div>
        </div>
        );
    return <section className='CustomMassageView'>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 justify-content-center">
                {listMassage}
                <BookModal show={modalShow} onHide={() => setModalShow(false)} 
                title={selectedTitle} 
                image={selectedImage}
                duration={selectedDuration}
                price={selectedPrice}
                description={selectedDescription}/>
            </div>
        </section>;
}

const massages = [
    {
        title:'Massage localisé (dos, nuque, épaules)', 
        image:photo1, 
        duration:'45 min',
        price:'50 €',
        description:'Massage localisé (dos, nuque, épaules) pour relâcher les tensions et apaiser le stress accumulé. Idéal pour une pause bien-être.', 
        id:1
    },
    {
        title:'Massage global du corps', 
        image:photo2, 
        duration:'1 h',
        price:'70 €',
        description:'Massage global du corps, associant pressions et effleurages doux pour détendre, rééquilibrer l’énergie et favoriser la relaxation profonde.', 
        id:2
    },
    {
        title:'Massage long et enveloppant', 
        image:photo3, 
        duration:'1 h 30',
        price:'90 €',
        description:'Massage long et enveloppant, combinant techniques relaxantes et appuyées pour libérer les tensions et reconnecter corps et esprit.', 
        id:3
    }
];