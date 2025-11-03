import './CustomMassage.css'
import HomeCard from '../HomeCard'
import PrestationCard from '../PrestationCard'
import BookModal from '../BookModal'
import { useEffect, useState } from 'react'
import axios from 'axios'
const apiUrl = import.meta.env.VITE_API_URL;

export default function CustomMassage({showDescription = true, variant}){
    const [prestations, setPrestations] = useState(null);
    const [selectedPrestation, setSelectedPrestation] = useState(null)
    const [modalShow, setModalShow] = useState(false);
    const [error, setError] = useState(null)
    
    useEffect(() => {
        async function getPrestations(){
            try {
                const result = await axios.get(`${apiUrl}/prestations/massages`)
                console.log('getPrestations=', result.data)
                setPrestations(result.data);
            } catch(err) {
                if(err.response){
                    setError(`Erreur serveur : ${err.response.data}`)
                }else if(err.request){
                    setError('Erreur réseau')
                }else{
                    setError('Erreur inconnue')
                }
            }
        }
        getPrestations();
    }, [])

    useEffect(()=>{
        if(selectedPrestation){console.log('selectedPrestation=', selectedPrestation)}
    })

    const listMassage = prestations?.map(massage =>
        showDescription 
        ? <div className='col' key={massage.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <PrestationCard
                    prestation={massage}
                    variant={variant}
                    setModalShow={setModalShow}
                    setSelectedPrestation={setSelectedPrestation}
                />
            </div>
        </div>
        : <div className='col' key={massage.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <HomeCard
                    title={massage.name}
                    image={`/photos/${massage.imagePath}`}
                />
            </div>
        </div>
        );
    return <section className='CustomMassageView'>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 justify-content-center">
                {listMassage} &&
                    <BookModal 
                        show={modalShow} 
                        onHide={() => setModalShow(false)} 
                        prestation={selectedPrestation}
                    />
             
                {error && <div className='text-center text-danger fw-bold'>{error}</div>}
            </div>
        </section>;
}