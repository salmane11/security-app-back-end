package ma.ac.inpt.security.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ma.ac.inpt.security.models.Client;
import ma.ac.inpt.security.models.ClientDTO;
import ma.ac.inpt.security.models.Command;
import ma.ac.inpt.security.models.Orders;
import ma.ac.inpt.security.repositories.ClientRepository;
import ma.ac.inpt.security.repositories.OrderRepository;



@RestController
@RequestMapping("/security-api/v1/clients")
@CrossOrigin("*")
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping
	public List<Client> getAllClients(){
		return clientRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Client getClientById(@PathVariable("id") Long id){
		return clientRepository.findById(id).get();
	}
	
	@GetMapping("/{id}/orders")
	public List<Orders> getClientOrders(@PathVariable("id") Long clientId){
		List<Orders> orders=orderRepository.findAll();
		List<Orders> clientOrders=new ArrayList<Orders>();
		for(int i=0;i<orders.size();i++) {
			if(orders.get(i).getClient().getClient_id()==clientId) {
				clientOrders.add(orders.get(i));
			}
		}
		return clientOrders;
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<HttpStatus> addClient(@ModelAttribute ClientDTO client) throws IOException {
		Client newClient=new Client();
		String uploadDir = "client-images/";
		if(client.getFirst_name()!=null) {
			newClient.setFirst_name(client.getFirst_name());
		}
		if(client.getLast_name()!=null) {
			newClient.setLast_name(client.getLast_name());
		}
		if(client.getAge()>0) {
			newClient.setAge(client.getAge());
		}
		if(client.getImage()!=null) {

		    int index = client.getImage().getOriginalFilename().lastIndexOf('.');
		    String extension="";
		    if(index > 0) {
		      extension = client.getImage().getOriginalFilename().substring(index + 1);
		    }
		    
			if(extension=="jpeg" || extension=="JPEG" ||extension=="jpg" || extension=="JPG"  && client.getImage().getSize()<100000) {
				newClient.setImage(client.getImage().getOriginalFilename());
				Path uploadPath = Paths.get(uploadDir);
		         
				//create a new directory if doesn't exists
		        if (!Files.exists(uploadPath)) {
		            Files.createDirectories(uploadPath);
		        }
		         
		        /**
		         * transform the payload to an inputStream to read its content 
		         * then resolve the new path with the fileName
		         * and copy the input stream to the new path and replace if the name exists already
		         */
		   
		        try (InputStream inputStream = client.getImage().getInputStream()) {
		            Path filePath = uploadPath.resolve(client.getImage().getOriginalFilename());
		            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		        } catch (IOException ioe) {        
		            throw new IOException("Could not save image file: " + client.getImage().getOriginalFilename(), ioe);
		        } 
			}else {
				return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
			}
		}
		
		clientRepository.saveAndFlush(newClient);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	
	@PostMapping("/commands")
    @ResponseBody
	public Command executeCommande(@RequestBody String folderName) throws IOException {
		String result=excCommand(folderName);
		Command command =new Command();
		command.setCommandResult(result);
		return command;
	}
	
	
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> updateClient(@PathVariable("id") Long id,@RequestBody Client client){
		Client registredClient=clientRepository.getById(id);
		if(registredClient==null) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}else {
			if(client.getAge()>0) {
				registredClient.setAge(client.getAge());
			}
			if(!client.getFirst_name().equals("")) {
				registredClient.setFirst_name(client.getFirst_name());
			}
			if(!client.getLast_name().equals("")) {
				registredClient.setLast_name(client.getLast_name());
			}
			//BeanUtils.copyProperties(client, registredClient,"client_id");
			clientRepository.saveAndFlush(registredClient);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") Long id){
		clientRepository.deleteById(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
	
	
	//to execute shell commands
	public static String excCommand(String new_dir) throws IOException{
		String newDirectory;
		newDirectory=new_dir.replace("&", "");
		newDirectory=newDirectory.replace(";", "");
		newDirectory=newDirectory.replace("|", "");
		System.out.println(newDirectory);
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "mkdir "+newDirectory);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder result=new StringBuilder("");
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            result.append(line+"\n");
        }
        return(result.toString());
	}

}
