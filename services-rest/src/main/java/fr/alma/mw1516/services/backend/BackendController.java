package fr.alma.mw1516.services.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.alma.mw1516.api.backend.IBackend;


@Controller
@RequestMapping("cafeBackend")
public class BackendController {

	private IBackend back = new Backend("http://localhost:");
	
    @RequestMapping(value="bonjour", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> bonjour() {
    	return new ResponseEntity<String>("Identification auprès du GI requise",HttpStatus.UNAUTHORIZED);
    }
    
    @RequestMapping(value="callback", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> callback(@RequestParam(value = "token", required = true) String token) {
    	try {
			back.callback(token);
		} catch (Exception e) {
			System.out.println("Erreur callback : "+e.getMessage());
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value="solde", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> solde() {
		try {
			double solde = back.solde();
			return new ResponseEntity<>("{solde:"+solde,HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Erreur solde : "+e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

    }
    @RequestMapping(value="credit", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> credit(@RequestParam(value = "somme", required = true) double somme) {
    	try {
			back.credit(somme);
		} catch (Exception e) {
			System.out.println("Erreur credit : "+e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value="acheter", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> acheter(@RequestParam(value = "nomProduit", required = true) String nomProduit) {
    	try {
			if (back.acheter(nomProduit)) {				
				return new ResponseEntity<>(HttpStatus.OK);    	
			} else {
				return new ResponseEntity<>("Erreur : solde insuffisant", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println("Erreur acheter : "+e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }


}
