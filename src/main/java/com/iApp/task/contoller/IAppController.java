package com.iApp.task.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iApp.task.common.PaginationConstants;
import com.iApp.task.model.EpaperRequest;
import com.iApp.task.service.IAppService;

import lombok.extern.slf4j.Slf4j;


@CrossOrigin
@RestController
@RequestMapping("/epaper")
@Slf4j
public class IAppController {
	
   
  @Autowired
  IAppService iAppService;
  
  @PostMapping("/uploadFile")
  public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file){
	return iAppService.saveDeviceInfo(file);
	  
  }
  
  @GetMapping("/deviceInfo")
  public ResponseEntity<List<EpaperRequest>> findDeviceInfo( @RequestParam(value = "pageNo", 
  		  defaultValue = PaginationConstants.DEFAULT_PAGE_NUM, required = false) int pageNo,
		  @RequestParam(value = "pageSize", defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
          @RequestParam(value = "sortBy", defaultValue = PaginationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
          @RequestParam(value = "sortDir", defaultValue = PaginationConstants.DEFAULT_SORT_TYPE, required = false) String sortDir) {
			return iAppService.findDeviceInfo(pageNo, pageSize, sortBy, sortDir);
	 
  }
  

}
