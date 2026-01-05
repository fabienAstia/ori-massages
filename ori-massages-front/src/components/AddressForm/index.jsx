import './AddressForm.css'

export default function AddressForm({address, setAddress}){

    return (
        <div>
            <div id='address'>
            <p><b>Adresse</b></p>
            <div className='row row-cols-2'>
                <div className='col-12 col-sm-3'>
                    <div className="mb-3">
                        <label htmlFor="street_number" className="form-label">N° :<span className="text-danger"> *</span></label>
                        <input 
                            type="text" 
                            className="form-control" 
                            id="street_number"
                            aria-describedby="street_number"
                            value={address.streetNumber}
                            onChange={(e) => setAddress({...address, streetNumber:e.target.value})}
                        />
                    </div>
                </div>
                <div className='col-12 col-sm-9'>
                    <div className="mb-3">
                        <label htmlFor="street_name" className="form-label">Rue :<span className="text-danger"> *</span></label>
                        <input 
                            type="text" 
                            className="form-control" 
                            id="street_name"
                            aria-describedby="street_name"
                            value={address.streetName}
                            onChange={(e) => setAddress({...address, streetName:e.target.value})}
                        />
                    </div>
                </div>
            </div>

            <div className="mb-3">
                <label htmlFor="complement" className="form-label">Complément :</label>
                <input 
                    // {...register("complement")}
                    type="text" 
                    className="form-control" 
                    id="complement"
                    aria-describedby="complement"
                    value={address.complement}
                    onChange={(e) => setAddress({...address, complement:e.target.value})}
                />
            </div>
            <div className='row row-cols-2'>
                <div className='col-12 col-sm-4'>
                    <div className="mb-3">
                        <label htmlFor="zip_code" className="form-label">Code postal :<span className="text-danger"> *</span></label>
                        <input 
                            // {...register("zip_code")}
                            type="text" 
                            className="form-control" 
                            id="zip_code"
                            aria-describedby="zip_code"
                            value={address.zipCode}
                            onChange={(e) => setAddress({...address, zipCode:e.target.value})}
                        />
                    </div>
                </div>
                <div className='col-12 col-sm-8'>
                    <div className="mb-3">
                        <label htmlFor="city" className="form-label">Ville :<span className="text-danger"> *</span></label>
                        <input 
                            // {...register("city_name")}
                            type="text" 
                            className="form-control" 
                            id="city"
                            aria-describedby="city"
                            value={address.city}
                            onChange={(e) => setAddress({...address, city:e.target.value})}
                        />
                    </div>
                </div>
            </div>
        </div>
        </div>
    )
}