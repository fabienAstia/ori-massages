import { useState } from 'react';
import './Contact.css'

export default function Contact(){
    const [charCount, setCharCount] = useState(0);
    const MAX_CHARS = 300;
    return(
        <section className="ContactView">
            <div className='text-center'>
                <h2 class="mb-3">Contact</h2>
                <p class="contact-text">
                    Une question, une demande de rendez-vous ou simplement l’envie d’échanger ?  
                    Je vous répondrai avec plaisir.
                </p>
            </div>
          
            <div className='d-flex mt-1 justify-content-center'>
                <form>
                    <div className="mb-3">
                        <label htmlFor="exampleInputEmail1" className="form-label">Email :<span className="red"> *</span></label>
                        <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"/>
                        <div id="emailHelp" className="form-text">Nous ne partageons jamais aucun email avec qui que ce soit.</div>
                    </div>
                    <div className='row row-cols-1 row-cols-md-2'>
                        <div className='col'>
                            <div className="mb-3">
                            <label htmlFor="exampleInputPassword1" className="form-label">Prénom :</label>
                            <input type="text" className="form-control" id="exampleInputPassword1"/>
                        </div>
                        </div>
                        <div className='col'>
                            <div className="mb-3">
                            <label htmlFor="exampleInputPassword1" className="form-label">Nom :</label>
                            <input type="text" className="form-control" id="exampleInputPassword1"/>
                        </div>
                        </div>
                    </div>
                    
                    <div className="mb-3">
                        <label htmlFor="exampleInputPassword1" className="form-label">Téléphone : </label>
                        <input type="tel" className="form-control" id="exampleInputPassword1"/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="exampleInputPassword1" className="form-label">Message : </label>
                        <textarea id='textarea' className='form-control w-100' 
                        maxLength={MAX_CHARS} rows={3} placeholder='Ecrire ici...'
                        onChange={
                            (event) => setCharCount(event.target.value.length)
                        }/>
                        <div id='remaining-char'>{MAX_CHARS-charCount} caractères restants</div>
                    </div>
                    <div className='justify-content-center d-flex'>
                        <button type="submit" className="btn btn-primary custom-btn">Submit</button>
                    </div>
                </form>
            </div>
            
        </section>
    );
}