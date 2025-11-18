import { useState } from 'react';
import './Contact.css'
import axios from 'axios';
import {useForm} from 'react-hook-form'

export default function Contact({bookModalSubmit, isAtHome}){
    const apiUrl = import.meta.env.VITE_API_URL
    const [data, setData] = useState(null)
    const [charCount, setCharCount] = useState(0);
    const MAX_CHARS = 300;
    const [phoneNumber, setPhoneNumber] = useState('')
    const {register, handleSubmit, setValue, formState:{errors}} = useForm();
    
    const  onChangePhoneNumber = (e) => {
        let formatted = (e.target.value).replace(/[^\d]/g, "")
        setPhoneNumber(formatted);
        return phoneNumber;
    }
    const onBlurPhoneNumber = (value) => {
        if(!value) return value
        const number = value.replace(/[\s]/g, "")
        setValue("phoneNumber", number)
        if(number.length < 3) return number.length;
        if(number.length >= 3){
            let chunk = '';
            for(let n = 0; n < (number.length/2); n++){
                chunk += `${number.slice(2*n, (2*(n+1)))} `;
            }
            return chunk;
        }
    }
    
    const onSubmit = async(data) => {
        if(bookModalSubmit){
            bookModalSubmit(data)
        }else{
            try {
                const res = await axios.post(`${apiUrl}/contact`, data);
                setData(res.data)
                console.log('data=', res.data)
            }catch(err){
                if(err.response){
                    console.error('POST FAILED with status= ' + err.response.status)
                } else {
                    console.error(err)
                    alert(err)
                }
            }
        }
    }

    return(
        <section className={`ContactView ${bookModalSubmit ? 'background' : ''}`}>
            <div className='text-center '>
                {!bookModalSubmit &&
                    <>
                        <h2 className="mb-3">Contact</h2>
                        <p className="contact-text">
                            Une question, une demande de rendez-vous ou simplement l’envie d’échanger ?  
                            Je vous répondrai avec plaisir.
                        </p>
                    </>
                }  
                {bookModalSubmit &&
                    <p className="contact-text">
                        Merci de renseigner vos informations pour confirmer votre réservation.
                    </p>
                }
               
            </div>
          
            <div className='d-flex mt-1 justify-content-center'>
                <form onSubmit={handleSubmit(onSubmit)}>

                    <div className={'row row-cols-2'}>
                        <div className='col-12 col-sm-4'>
                            {bookModalSubmit &&
                            <div className="mb-3">
                                <label htmlFor="phoneNumber" className="form-label">Téléphone : <span className="red"> *</span></label>
                                <input 
                                    {...register("phoneNumber", {
                                        required:true, 
                                        onChange: (e) => { onChangePhoneNumber(e)},
                                        onBlur: (e) => {e.target.value = onBlurPhoneNumber(e.target.value)}
                                    })}
                                    type="tel" 
                                    className="form-control" 
                                    id="phoneNumber"
                                    maxLength={14}
                                    aria-describedby="phone_number"
                                    required
                                />
                                {errors.phoneNumber && <span className='text-danger fw-bold'>This field is required</span>}
                            </div>
                            }
                        </div>

                        <div className={"col-12 " + (bookModalSubmit ? 'col-sm-8' : 'col-sm-12')}>
                            <div className="mb-3">
                                <label htmlFor="fullname" className="form-label">Nom :</label>
                                <input 
                                    {...register("fullname")}
                                    type="text" 
                                    className="form-control" 
                                    id="fullname"
                                    aria-describedby="fullname"
                                />
                            </div>
                        </div>
                    </div>
        

                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email :<span className="red"> *</span></label>
                        <input 
                            {...register("email", {required:true})}
                            type="email" 
                            className="form-control" 
                            id="email" 
                            aria-describedby="email"
                        />
                        <div id="emailHelp" className="form-text">Nous ne partageons jamais aucun email avec qui que ce soit.</div>
                        {errors.email && <span className='text-danger fw-bold'>This field is required</span>}
                    </div>


                    {isAtHome &&
                    <div id='address'>
                        <p><b>Adresse</b></p>
                        <div className='row row-cols-2'>
                            <div className='col-12 col-sm-3'>
                                <div className="mb-3">
                                    <label htmlFor="street_number" className="form-label">N° :<span className="red"> *</span></label>
                                    <input 
                                        {...register("street_number")}
                                        type="text" 
                                        className="form-control" 
                                        id="street_number"
                                        aria-describedby="street_number"
                                    />
                                </div>
                            </div>
                            <div className='col-12 col-sm-9'>
                                <div className="mb-3">
                                    <label htmlFor="street_name" className="form-label">Rue :<span className="red"> *</span></label>
                                    <input 
                                        {...register("street_name")}
                                        type="text" 
                                        className="form-control" 
                                        id="street_name"
                                        aria-describedby="street_name"
                                    />
                                </div>
                            </div>
                        </div>

                        <div className="mb-3">
                            <label htmlFor="complement" className="form-label">Complément :</label>
                            <input 
                                {...register("complement")}
                                type="text" 
                                className="form-control" 
                                id="complement"
                                aria-describedby="complement"
                            />
                        </div>
                        <div className='row row-cols-2'>
                            <div className='col-12 col-sm-4'>
                                <div className="mb-3">
                                    <label htmlFor="zip_code" className="form-label">Code postal :<span className="red"> *</span></label>
                                    <input 
                                        {...register("zip_code")}
                                        type="text" 
                                        className="form-control" 
                                        id="zip_code"
                                        aria-describedby="zip_code"
                                    />
                                </div>
                            </div>
                            <div className='col-12 col-sm-8'>
                                <div className="mb-3">
                                    <label htmlFor="city_name" className="form-label">Ville :<span className="red"> *</span></label>
                                    <input 
                                        {...register("city_name")}
                                        type="text" 
                                        className="form-control" 
                                        id="city_name"
                                        aria-describedby="city_name"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                    }
                    

                    <div className="mb-3">
                        <label htmlFor="message" className="form-label">Message : </label>
                        <textarea 
                            {...register("message", {onChange:(e) => setCharCount(e.target.value.length)})}
                            id='message' 
                            className='form-control w-100' 
                            maxLength={MAX_CHARS} rows={3} placeholder='Ecrire ici...'
                        />
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