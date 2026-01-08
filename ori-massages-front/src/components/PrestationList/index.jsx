import './PrestationList.css'
import HomeCard from '../HomeCard'
import PrestationCard from '../PrestationCard'
import { useEffect, useState, lazy } from 'react'
const BookModal = lazy(() => import('../BookModal'))
const apiUrl =  import.meta.env.VITE_API_URL

export default function PrestationList({showDescription = true, variant, prestations}){
    const [selectedPrestation, setSelectedPrestation] = useState(null)
    const [modalShow, setModalShow] = useState(false);
    
    useEffect(()=>{
        if(selectedPrestation){console.log('selectedPrestation=', selectedPrestation)}
    })

    const listPrestation = prestations?.map(prestation =>
        showDescription 
        ? <div className='col' key={prestation.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <PrestationCard
                    prestation={prestation}
                    variant={variant}
                    setModalShow={setModalShow}
                    setSelectedPrestation={setSelectedPrestation}
                />
            </div>
        </div>
        : <div className='col' key={prestation.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <HomeCard
                    title={prestation.name}
                    image={`${apiUrl}/uploads/prestations/${prestation.imagePath}`}
                />
            </div>
        </div>
        );

    return <section className='PrestationView'>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 justify-content-center">
                {listPrestation} 
                <BookModal 
                    show={modalShow} 
                    onHide={() => setModalShow(false)} 
                    prestation={selectedPrestation}
                />
            </div>
        </section>;
}