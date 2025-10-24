import './FacialCare.css';
import PrestationCard from '../PrestationCard'
import BookModal from '../BookModal'
import HomeCard from '../HomeCard';
import {useEffect, useState } from 'react';
import axios from 'axios';

export default function FacialCare({showDescription=true, variant}){
    const [prestations, setPrestations] = useState(null);
    const [selectedPrestation, setSelectedPrestation] = useState(null)
    const [modalShow, setModalShow] = useState(false);
    // const [selectedTitle, setSelectedTitle] = useState('');
    // const [selectedImage, setSelectedImage] = useState('');
    // const [selectedPrice, setSelectedPrice] = useState('');
    // const [selectedDuration, setSelectedDuration] = useState(0);
    // const [selectedDescription, setSelectedDescription] = useState('');

    useEffect(() => {
        async function getPrestations(){
            try {
                const result = await axios.get('http://localhost:8080/prestations/soins-visage')
                console.log(result.data, 'result')
                return setPrestations(result.data);
            } catch(err) {
                if(err.response){
                    alert('err =', err.response.data)
                }else if(err.request){
                    alert('err', err.request)
                }
            }
        }
        getPrestations();
    }, [])

    const listFacialCare = prestations?.map(care =>
        showDescription ?
        <div className='col' key={care.id}>
            <div className='card-body d-flex flex-column gap-3 mb-4 align-items-center'>
                <PrestationCard 
                    prestation={care}
                    variant={variant}
                    setModalShow={setModalShow}
                    setSelectedPrestation={setSelectedPrestation}
                    // title={care.name}
                    // description={showDescription? care.description : undefined}
                    // price={care.price}
                    // image={`/photos/${care.imagePath}`}
                    // duration={care.duration}
                    // variant={variant}
                    // setModalShow={setModalShow}
                    // setSelectedTitle={setSelectedTitle}
                    // setSelectedImage={setSelectedImage}
                    // setSelectedPrice={setSelectedPrice}
                    // setSelectedDuration={setSelectedDuration}
                    // setSelectedDescription={setSelectedDescription}
                />
            </div>
        </div>
        :
        <div className='col' key={care.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <HomeCard
                    title={care.name}
                    image={`/photos/${care.imagePath}`}
                />
            </div>
        </div>
    );
    return  <section className='CustomMassageView'>
                <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 justify-content-center">
                    {listFacialCare}
                    <BookModal 
                        show={modalShow} 
                        onHide={() => setModalShow(false)} 
                        prestation={selectedPrestation}
                        // title={selectedTitle} 
                        // image={selectedImage}
                        // duration={selectedDuration}
                        // price={selectedPrice}
                        // description={selectedDescription}
                    />
                </div>
            </section>;
}