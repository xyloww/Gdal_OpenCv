package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	public String executeGdalString() {
		String[] commands  = "gdal_translate -of GTiff -co -COMPRESS=JPEG -co \"TILED=YES\" land_shallow_topo_8192.tif output.jpeg".split(" ");
		try {
			Process p = Runtime.getRuntime().exec(commands);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;

			try {
				while ((line = input.readLine())!= null) {
					LOGGER.info(line);
				}
			} catch (IOException exception) {
				LOGGER.error(null, exception);
			}

			p.getErrorStream().close();
			p.getInputStream().close();
			p.getOutputStream().close();
			p.destroy();

			
		} catch (IOException exception) {
			LOGGER.error(null, exception);
		}
		return "done";
	}

	@GetMapping(value = "/output") 
	public ResponseEntity<UrlResource> getOutput() {
		Path path = Paths.get("./");
		try {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, 
			"attachment; filename=\"output.jpeg\"").body(new UrlResource(path.resolve("output.jpeg").toUri()));
		
		} catch (MalformedURLException e){
			LOGGER.error(null, e);
		}
		return null;
		

	}
	

}
