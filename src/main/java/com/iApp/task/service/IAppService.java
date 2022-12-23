package com.iApp.task.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.iApp.task.model.EpaperRequest;
import com.iApp.task.repository.EpaperRequestRepository;

@Service
public class IAppService {
	
	private static Logger logger = LoggerFactory.getLogger(IAppService.class);
	
	@Autowired
	EpaperRequestRepository ePaperRepo;
	
	public ResponseEntity<String> saveDeviceInfo(MultipartFile file) {
		  if (!file.isEmpty()) {
	          try {
	        	  
	        	  EpaperRequest epaperReq = EpaperRequest.builder().fileName(file.getOriginalFilename()).uploadTime(LocalDateTime.now()).build();

	        	  final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	              final DocumentBuilder builder = factory.newDocumentBuilder();
	              Document doc = builder.parse(file.getInputStream());
	              NodeList deviceInfo = doc.getElementsByTagName("deviceInfo");

	              for (int i = 0; i < deviceInfo.getLength(); i++) {
	                  Node deviceTag = deviceInfo.item(i);
	                  if (deviceTag.getNodeType() == Node.ELEMENT_NODE) {
	                      NodeList childNodes = deviceTag.getChildNodes();
	                      for (int j = 0; j < childNodes.getLength(); j++) {
	                          Node innerDeviceTag = childNodes.item(j);
	                          if (innerDeviceTag.getNodeType() == Node.ELEMENT_NODE) {
	                              if ("screenInfo".equalsIgnoreCase(innerDeviceTag.getNodeName())) {
	                            	  String width = innerDeviceTag.getAttributes().getNamedItem("width").getTextContent();
	                                  String height = innerDeviceTag.getAttributes().getNamedItem("height").getTextContent();
	                                  String dpId = innerDeviceTag.getAttributes().getNamedItem("dpi").getTextContent();
	                                  epaperReq.setWidth(Long.parseLong(width));
	                                  epaperReq.setHeight(Long.parseLong(height));
	                                  epaperReq.setDpi(Long.parseLong(dpId));
	                                  
	                              }
	                              if ("appInfo".equalsIgnoreCase(innerDeviceTag.getNodeName())) {
	                            	  NodeList appChildNodes = innerDeviceTag.getChildNodes();
	                            	  for (int k = 0; k < appChildNodes.getLength(); k++) {
	                                      Node appNode = appChildNodes.item(k);
	                                      if (appNode.getNodeType() == Node.ELEMENT_NODE) {
	                                          if ("newspaperName".equalsIgnoreCase(appNode.getNodeName())) {
	                                        	  String newPaper = appNode.getTextContent();
	                                              epaperReq.setNewspaperName(newPaper);
	                                          }
	                                      }

	                                  }
	                              }
	                          }

	                      }

	                  }
	              }
	              ePaperRepo.save(epaperReq);
	          } catch (Exception e) {
	        	  ResponseEntity.ok().body("some problem");
	          }
		      return ResponseEntity.ok().body("Info received successfully");

	      }
		return  ResponseEntity.badRequest().body("No File");
	}
	
	public  ResponseEntity<List<EpaperRequest>> findDeviceInfo(int pageNo, int pageSize, String sortBy, String sortDir){
		try {
	    	
	    	 Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
	                 : Sort.by(sortBy).descending();
	    	  Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

	          Page<EpaperRequest> posts = ePaperRepo.findAll(pageable);

	          List<EpaperRequest> listOfPosts = posts.getContent();

		      if (listOfPosts.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }
	      return new ResponseEntity<>(listOfPosts, HttpStatus.OK);
		  } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		  }
	}

}
