import './LegalNotices.css'

export default function LegalNotices(){
    return (
        <section className="legalNoticesView h-100">
            <div className="d-flex flex-column container text-center justify-content-center align-items-center h-100">
                <h1>Mentions légales</h1>
                <div className="text-start">
                    <p><b>Éditeur :</b> Fabien Astiasaran</p>
                    <p><b>Adresse :</b> Paris, France</p>
                    <p><b>Email :</b> <a href="mailto:fabien.astiasaran@gmail.com" target='_blank'>fabien.astiasaran@gmail.com</a></p>
                    <p><b>Contexte :</b> Site de réservation en ligne pour massages bien-être et soins du visage proposés par Oriana Lanfossi, praticienne indépendante</p>

                    <p><b>Hébergeur :</b> OVH SAS<br />
                        2 rue Kellermann – 59100 Roubaix – France<br />
                        <a href="https://www.ovhcloud.com/fr/" target="_blank" rel="noopener noreferrer">https://www.ovhcloud.com/</a>
                    </p>

                    <h2>Réservation et prestations</h2>
                    <p>
                        Les réservations de massages et de soins sont proposées dans la limite des créneaux disponibles. 
                        Toute demande de réservation vaut proposition et sera confirmée par l’éditeur. 
                        Les prestations proposées n’ont aucun caractère médical et relèvent exclusivement du bien-être.
                    </p>

                    <h2>Données personnelles</h2>
                    <p>Lors de la réservation d’un créneau, certaines données personnelles peuvent être collectées (nom, prénom, email, numéro de téléphone, date et heure de réservation). 
                        Ces données sont utilisées uniquement pour la gestion des rendez-vous et la communication avec les clients, conformément au Règlement Général sur la Protection des Données (RGPD).
                    </p>
                    <p>Les données ne sont ni revendues ni transmises à des tiers et sont conservées pendant la durée strictement nécessaire à la gestion des prestations. 
                        Vous pouvez exercer vos droits d’accès, de rectification ou de suppression de vos données en écrivant à l’adresse : 
                        <a href="mailto:fabien.astiasaran@gmail.com" target='_blank'>fabien.astiasaran@gmail.com</a>.
                    </p>

                    <h2>Responsabilité</h2>
                    <p>L’éditeur s’efforce de fournir des informations exactes et à jour, mais ne saurait être tenu responsable des erreurs, omissions ou indisponibilités temporaires du site.
                         La praticienne reste seule responsable du contenu, de la réalisation et de la conformité des prestations proposées.
                    </p>

                    <h2>Règlement des litiges</h2>
                    <p>En cas de litige, le client est invité à s’adresser en priorité à la praticienne, <a href=":mailto:olanfossi@gmail.com" target='_blank'>Oriana Lanfossi</a>, afin de rechercher une solution amiable. 
                    À défaut d’accord, le litige pourra être porté devant les tribunaux compétents conformément à la législation française.
                    </p>
                    {/* Si la praticienne est inscrite en tant qu’auto-entrepreneuse, tu peux préciser son nom complet et éventuellement un organisme de médiation (obligatoire en cas de prestations à des particuliers). */}

                    <h2>Propriété intellectuelle</h2>
                    <p>L’ensemble des contenus de ce site (textes, images, code) sont la propriété exclusive de Fabien Astiasaran, sauf mention contraire.
                        Toute reproduction, distribution ou utilisation sans autorisation écrite préalable est interdite.
                    </p>

                </div>
            </div>
        </section>
    );
}