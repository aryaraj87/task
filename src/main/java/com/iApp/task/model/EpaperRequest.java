package com.iApp.task.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "epaper_request",schema = "task")
public class EpaperRequest {

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;

	  @Column(name = "newspaperName")
	  private String newspaperName;
	
	  @Column(name = "width")
	  private long width;

	  @Column(name = "height")
	  private long height;
	  
	  @Column(name = "dpi")
	  private long dpi;
	  
	  @Column(name = "file_name")
	  private String fileName;
	  
	  @Column(name = "upload_time")
	  private LocalDateTime uploadTime;
	
}
