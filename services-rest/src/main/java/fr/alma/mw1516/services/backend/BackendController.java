package fr.alma.mw1516.services.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
    	return new ResponseEntity<String>("",HttpStatus.OK);
    }
    
    @RequestMapping(value="callback", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> callback(@RequestParam(value = "token", required = false) String token) {
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
    	return new ResponseEntity<>(HttpStatus.OK);

    }
    @RequestMapping(value="credit", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> credit() {
    	return new ResponseEntity<>(HttpStatus.OK);

    }
    @RequestMapping(value="acheter", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> acheter() {
    	return new ResponseEntity<>(HttpStatus.OK);
    	
    }


}
