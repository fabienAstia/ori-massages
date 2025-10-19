import './PrivacyPolicy.css'

export default function PrivacyPolicy(){
    return (
    <section className="privacyPolicyView h-100">
      <div className="d-flex flex-column container text-center justify-content-center align-items-center h-100">
        <h1>Politique de confidentialité</h1>
        <div className="text-start">
          <h2>1. Éditeur du site</h2>
          <p>
            Ce site est édité par <b>Fabien Astiasaran</b> dans le cadre d’un portfolio personnel.
          </p>

          <h2>2. Données collectées</h2>
          <p>
            Ce site n’utilise aucun cookie ni service d’analyse.  
            La seule donnée susceptible d’être collectée est votre adresse email, 
            lorsque vous choisissez de me contacter volontairement.  
            Ces échanges sont traités de manière confidentielle et ne sont conservés que dans votre messagerie.
          </p>

          <h2>3. Hébergement</h2>
          <p>
            Le site est hébergé par <b>OVH SAS</b>, 2 rue Kellermann – 59100 Roubaix – France.
          </p>

          <h2>4. Cookies</h2>
          <p>
            Ce site ne dépose aucun cookie de suivi, de publicité ou de mesure d’audience.  
            Certaines ressources externes (par exemple Google Fonts) peuvent, selon votre navigateur, 
            générer des requêtes externes anonymisées.
          </p>

          <h2>5. Finalité du site</h2>
          <p>
            Ce site a pour seule finalité la présentation des projets et compétences de Fabien Astiasaran.
            Aucune donnée utilisateur n’est exploitée à des fins commerciales.
          </p>

          <h2>6. Droits de l’utilisateur</h2>
          <p>
            Conformément au Règlement Général sur la Protection des Données (RGPD), 
            vous pouvez à tout moment demander la suppression des échanges ou informations personnelles 
            que vous auriez transmis par email.
          </p>

          <h2>7. Contact</h2>
          <p>
            Pour toute question relative à la protection de vos données, vous pouvez me contacter à l’adresse suivante :  
            <a href="mailto:fabien.astiasaran@gmail.com" target='_blank'> fabien.astiasaran@gmail.com</a>
          </p>
        </div>
      </div>
    </section>
    );
}