package fr.alma.mw1516.services.backend;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.alma.mw1516.api.backend.IBackend;
import fr.alma.mw1516.api.backend.IUser;
import fr.alma.mw1516.services.backend.util.MapDB;


@Controller
@RequestMapping("cafeBackend")
public class BackendController {

	private IBackend back = new Backend("http://localhost:8080/ame-services-rest/service");
	private MapDB<String, String> authDb = new MapDB<>("auth", "auth");
	
    @RequestMapping(value="bonjour", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> bonjour(@RequestHeader(value="auth-token", required=false) String authToken) {
    	if (authToken != null && authDb.get(authToken) != null) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	} else {
    		return new ResponseEntity<String>("Identification auprès du GI requise",HttpStatus.UNAUTHORIZED);
    	}
    }
    
    @RequestMapping(value="callback", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> callback(@RequestParam(value = "token", required = true) String token) {
    	try {
			IUser user = back.callback(token);
			//Créer la session
			UUID auth = UUID.randomUUID();
			authDb.put(auth.toString(), user.getId());
			//mettre notre token dans le header
			ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
			response.getHeaders().set("auth-token", auth.toString());
			return response;
		} catch (Exception e) {
			System.out.println("Erreur callback : "+e.getMessage());
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}    	
    }
    @RequestMapping(value="solde", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> solde(@RequestHeader(value="auth-token", required=true) String authToken) {
    	String uid = null;
    	if (authToken == null || authDb.get(authToken) == null) {
    		return new ResponseEntity<String>("Identification auprès du GI requise",HttpStatus.UNAUTHORIZED);
    	} else {
    		uid = authDb.get(authToken);
    	}
		try {
			double solde = back.solde(uid);
			return new ResponseEntity<>("{solde:"+solde,HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Erreur solde : "+e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

    }
    @RequestMapping(value="credit", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> credit(@RequestHeader(value="auth-token", required=true) String authToken,
    		@RequestParam(value = "somme", required = true) double somme) {
    	
    	String uid = null;
    	if (authToken == null || authDb.get(authToken) == null) {
    		return new ResponseEntity<String>("Identification auprès du GI requise",HttpStatus.UNAUTHORIZED);
    	} else {
    		uid = authDb.get(authToken);
    	}
    	try {
			back.credit(uid, somme);
		} catch (Exception e) {
			System.out.println("Erreur credit : "+e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value="acheter", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> acheter(@RequestHeader(value="auth-token", required=true) String authToken,
    		@RequestParam(value = "nomProduit", required = true) String nomProduit) {
    	
    	String uid = null;
    	if (authToken == null || authDb.get(authToken) == null) {
    		return new ResponseEntity<String>("Identification auprès du GI requise",HttpStatus.UNAUTHORIZED);
    	} else {
    		uid = authDb.get(authToken);
    	}
    	try {
			if (back.acheter(uid, nomProduit)) {				
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
