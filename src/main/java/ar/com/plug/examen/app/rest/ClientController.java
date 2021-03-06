package ar.com.plug.examen.app.rest;

import ar.com.plug.examen.aspect.LogginAspect;
import ar.com.plug.examen.domain.exceptions.ClientDoesNotExistException;
import ar.com.plug.examen.domain.model.Client;
import ar.com.plug.examen.domain.service.impl.ClientServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Client API")
public class ClientController {

    @Autowired
    private ClientServiceImpl service;

    @GetMapping(value = "/clients")
    @LogginAspect
    @ApiOperation(value = "Get the client list", notes = "Return the list of clients" )
    public ResponseEntity<List<Client> > getClients(){
        List<Client> clients = this.service.findAll();
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping(value = "/client/{id}")
    @LogginAspect
    @ApiOperation(value = "Find a client", notes = "Return a client by Id" )
    public ResponseEntity<Client> getClient(@PathVariable("id") Long id){
        Client client = null;
        try {
            client = this.service.findById(id);
        } catch (ClientDoesNotExistException e) {
            return new ResponseEntity<>(client, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(client);
    }

    @PostMapping(path="/client", produces = {MediaType.APPLICATION_JSON_VALUE },
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @LogginAspect
    @ApiOperation(value = "Add a client", notes = "Return the client saved passed by param" )
    public ResponseEntity<Client> createClient(@RequestBody Client aClient){
        Client client = this.service.saveClient(aClient);
        return ResponseEntity.ok().body(client);
    }

    @PutMapping(path = "/client", produces = {MediaType.APPLICATION_JSON_VALUE },
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @LogginAspect
    @ApiOperation(value = "Update a client", notes = "Return the client updated referenced by id" )
    public ResponseEntity<?> updateClient(@RequestBody Client aClient){
        Client client = null;
        try {
            client = this.service.updateClient(aClient);
        } catch (ClientDoesNotExistException e) {
            return new ResponseEntity<>("The client does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(client);
    }

    @DeleteMapping(path="/client/delete/{id}")
    @LogginAspect
    @ApiOperation(value = "Delete a client", notes = "Delete the client referenced by id" )
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        this.service.deleteClient(id);
        return ResponseEntity.ok().build();
    }

}
