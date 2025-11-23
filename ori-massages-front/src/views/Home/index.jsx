import './Home.css'
import logo from '../../assets/photos/logo-fond-blanc-espace.svg'
import oriana_photo from '../../assets/photos/oriana-black-and-white-removebg.png'
import {Link, NavLink} from 'react-router-dom';
import Prestation from '../../components/Prestation';
import Footer from '../../components/Footer'

export default function Home(){
    return (
        <>
        <section className='HomeView'>
            <div className='background'>
                <img src={logo} className='logo'/>
                <div className='hero d-flex '>
                    <h1 >
                        Offrez-vous un moment de détente et d’harmonie, au cœur d’un cadre naturel et apaisant.
                    </h1>
                    <nav className='row justify-content-evenly g-3 g-md-5 mt-sm-1 mt-md-2'>
                        <div className='col-12 col-sm-6 col-md-3 d-flex  justify-content-center'>
                            <NavLink to="/services" className="navButton btn btn-outline-light w-100">Prestations</NavLink>
                        </div>
                        <div className='col-12 col-sm-6 col-md-3 d-flex  justify-content-center'>
                            <NavLink to="/about-me" className='navButton btn btn-outline-light w-100'>À propos</NavLink>
                        </div>
                        <div className='col-12 col-sm-6 col-md-3 d-flex justify-content-center'>
                            <NavLink to="/solidarity-fund" className='navButton btn btn-outline-light w-100'>Cagnotte solidaire</NavLink>
                        </div>
                        <div className='col-12 col-sm-6 col-md-3 d-flex  justify-content-center'>
                            <NavLink to="/contact" className='navButton btn btn-outline-light w-100'>Contact</NavLink>
                        </div>
                    </nav>
                </div>
            </div>

            <section className='services-preview'>
                <div className='d-flex justify-content-center'>
                    <p className='intro-text'>
                        Parce que chacun.e doit avoir la possibilité de prendre soin de soi. 
                    </p>
                </div>
            
                <h2>Massages sur mesures</h2>
                <Prestation 
                    showDescription={false}
                    typeOfPrestation='massages'
                />
                <div className='d-flex justify-content-center mt-5'>
                    <NavLink to="/services" className="navButton btn btn-outline-primary ">Voir plus</NavLink>
                </div>

                <h2>Soins visage</h2>
                <Prestation 
                    showDescription={false}
                    typeOfPrestation='facial-cares'
                />
                <div className='d-flex justify-content-center mt-5'>
                    <NavLink to="/services" className="navButton btn btn-outline-primary ">Voir plus</NavLink>
                </div>              
            </section>
            <section className='about-me-preview'>
                <h2>À propos</h2>
                <div className='d-flex'>
                    <div className='row row-cols-1 row-cols-md-2'>
                         <div className='photo-wrapper col'>
                            <img src={oriana_photo} alt="photo de moi" className='ori-photo'/>
                        </div>
                        <div className='about-text col'>
                            <p>
                            Formée à l'école ...
                            </p>
                            <p>
                            Travailleuse sociale pendant plusieurs années, j’ai rencontré de nombreuses personnes, 
                            dans des contextes complexes et de forts moments de vulnérabilité.
                            </p>
                            <p> 
                            J’ai découvert combien un moment de détente, un soin attentif ou un massage bienveillant 
                            peuvent être des leviers puissants pour aider à restaurer la confiance en soi, à se reconnecter.
                            </p>
                            <p>
                            Le bien-être agit comme un catalyseur, permettant de restaurer une énergie positive, 
                            de renforcer l’estime de soi et de renouer avec une dynamique de changement personnel durable.
                            </p>
                        </div>
                       
                    </div>
                </div>
                <div className='d-flex justify-content-center mt-5'>
                        <NavLink to="/services" className="navButton btn btn-outline-primary ">Me contacter</NavLink>
                </div> 
            </section>
            <Footer></Footer>
        </section>

        </>
    );
}